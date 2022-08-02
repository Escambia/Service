#![forbid(unsafe_code)]
use crate::websocket::handler;
use crate::websocket::server::{ChatServer, ChatServerHandle};
use actix_web::web::{Data, Query};
use actix_web::{web, App, Error, HttpRequest, HttpResponse, HttpServer, Responder};
use anyhow::Result;
use dotenv::dotenv;
use sqlx::PgPool;
use std::collections::HashMap;
use std::env;
use tokio::task::{spawn, spawn_local};

pub mod router;
pub mod service;
pub mod websocket;

pub type ConnId = usize;
pub type RoomId = String;
pub type UserId = String;
pub type Msg = String;

#[global_allocator]
static GLOBAL: mimalloc::MiMalloc = mimalloc::MiMalloc;

#[tokio::main(flavor = "current_thread")]
async fn main() -> Result<()> {
    dotenv().ok();
    env_logger::init();

    let database_url = env::var("DATABASE_URL").unwrap_or_else(|_| "postgresql://postgres:postgres@database.c7zj1xnu7zet.us-west-2.rds.amazonaws.com:5432/postgres".to_string());
    let db_pool = PgPool::connect(&database_url).await?;

    let (chat_server, server_tx) = ChatServer::new();
    let chat_server = spawn(chat_server.run());

    HttpServer::new(move || {
        App::new()

            .app_data(Data::new(db_pool.clone()))
            .app_data(Data::new(server_tx.clone()))
            .route("/escambia/chat", web::get().to(index))
            .service(web::resource("/escambia/chat/ws").route(web::get().to(chat_ws)))
            .service(web::scope("/escambia/chat").configure(router::init))
    })
    .bind(("0.0.0.0", 8081))
    .expect("Unable to bind port")
    .run()
    .await?;

    chat_server.await?.expect("TODO: panic message");

    Ok(())
}

async fn index() -> impl Responder {
    HttpResponse::Ok()
        .append_header(("content-type", "text/html"))
        .body(
            r#"
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

                                                          "#,
        )
}

async fn chat_ws(
    req: HttpRequest,
    stream: web::Payload,
    chat_server: Data<ChatServerHandle>,
    query: Query<HashMap<String, String>>,
    pool: Data<PgPool>,
) -> Result<HttpResponse, Error> {
    let (res, session, msg_stream) = actix_ws::handle(&req, stream)?;
    spawn_local(handler::chat_ws(
        (**chat_server).clone(),
        session,
        msg_stream,
        query.get("room").unwrap().to_owned(),
        query.get("user").unwrap().to_owned(),
        pool,
    ));

    Ok(res)
}
