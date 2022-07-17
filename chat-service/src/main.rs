#[macro_use]
extern crate diesel;
extern crate env_logger;

mod db;
mod chat;
mod schema;
mod models;
mod server;
mod handler;

use chat::*;
use db::{establish_connection, PgPool};
use actix_web::{App, Error, HttpRequest, HttpResponse, HttpServer, Responder, web::{Data}, web};
use utoipa::{OpenApi};
use utoipa_swagger_ui::{SwaggerUi};
use std::env;
use actix_web::middleware::{Logger};
use crate::server::{ChatServer, ChatServerHandle};
use tokio::{
    task::{spawn, spawn_local},
    try_join,
};

pub type ConnId = usize;
pub type RoomId = String;
pub type Msg = String;

#[derive(Clone)]
pub struct Context {
    pub db: PgPool,
}

#[tokio::main(flavor = "current_thread")]
async fn main() -> std::io::Result<()> {
    let (chat_server, server_tx) = ChatServer::new();
    let chat_server = spawn(chat_server.run());
    if env::var("PROFILE").unwrap() == "prod" {
        env::set_var("RUST_LOG", "actix_web=info,info");
    } else {
        env::set_var("RUST_LOG", "actix_web=debug,debug");
    }
    env_logger::init();

    #[derive(OpenApi)]
    #[openapi(
    components(
    GetChatRoomInfo,
    AddChatRoomRequest,
    UpdateChatRoomRequest,
    ),
    handlers(
    get_chat_room_info,
    create_chat_room,
    update_chat_room
    )
    )]
    struct ApiDoc;

    let pool = establish_connection();

    let http_server = HttpServer::new(move || {
        App::new()
            .wrap(Logger::default())
            .wrap(Logger::new("%a %{User-Agent}i"))
            .route("/", web::get().to(index))
            .route("/getChatRoomInfo", web::post().to(get_chat_room_info))
            .route("/createChatRoom", web::post().to(create_chat_room))
            .route("/updateChatRoom", web::patch().to(update_chat_room))
            .service(web::resource("/ws").route(web::get().to(chat_ws)))
            .service(SwaggerUi::new("/swagger-ui/{_:.*}").url("/api-doc/openapi.json", ApiDoc::openapi()))
            .app_data(Data::new(Context { db: pool.clone() }))
            .app_data(web::Data::new(server_tx.clone()))
    })
        .bind(("127.0.0.1", 8081))
        .expect("Unable to bind port")
        .run();

    match try_join!(http_server, async move { chat_server.await.unwrap() }) {
        Ok(_) => Ok(()),
        Err(e) => Err(e),
    }
}

async fn index() -> impl Responder {
    HttpResponse::Ok()
        .append_header(("content-type", "text/html"))
        .body(r#"
        _____                        _     _
       |  ___|                      | |   (_)
       | |__ ___  ___ __ _ _ __ ___ | |__  _  __ _
       |  __/ __|/ __/ _` | '_ ` _ \| '_ \| |/ _` |
       | |__\__ \ (_| (_| | | | | | | |_) | | (_| |
       \____/___/\___\__,_|_| |_| |_|_.__/|_|\__,_|


  _____ _           _   _____                 _
 /  __ \ |         | | /  ___|               (_)
 | /  \/ |__   __ _| |_\ `--.  ___ _ ____   ___  ___ ___
 | |   | '_ \ / _` | __|`--. \/ _ \ '__\ \ / / |/ __/ _ \
 | \__/\ | | | (_| | |_/\__/ /  __/ |   \ V /| | (_|  __/
  \____/_| |_|\__,_|\__\____/ \___|_|    \_/ |_|\___\___|

                                                          "#)
}

async fn chat_ws(
    req: HttpRequest,
    stream: web::Payload,
    chat_server: Data<ChatServerHandle>,
) -> Result<HttpResponse, Error> {
    let (res, session, msg_stream) = actix_ws::handle(&req, stream)?;
    spawn_local(handler::chat_ws(
        (**chat_server).clone(),
        session,
        msg_stream,
    ));

    Ok(res)
}
