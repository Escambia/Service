#[macro_use]
extern crate diesel;
extern crate env_logger;

mod db;
mod chat;
mod schema;
mod models;

use db::{establish_connection, PgPool};
use actix_web::{App, HttpResponse, HttpServer, Responder, web::{Data}, web};
use actix_web::middleware::{Logger};

#[derive(Clone)]
pub struct Context {
    pub db: PgPool,
}

#[actix_rt::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "actix_web=debug,debug");
    env_logger::init();

    let pool = establish_connection();

    HttpServer::new(move || {
        App::new()
            .wrap(Logger::default())
            .wrap(Logger::new("%a %{User-Agent}i"))
            .route("/", web::get().to(index))
            .route("/createChatRoom", web::post().to(chat::create_chat_room))
            .app_data(Data::new(Context {
                db: pool.clone()
            }))
    })
        .bind(("127.0.0.1", 8081))
        .expect("Unable to bind port")
        .run()
        .await
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
