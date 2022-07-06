extern crate diesel;
extern crate env_logger;

use actix_web::{get, App, HttpResponse, HttpServer, Responder, web::{Data}};
use actix_web::middleware::{Logger};

use diesel::{RunQueryDsl};
use diesel::prelude::*;
use chat_service::{establish_connection, PgPool};

use chat_service::models::UserInfo;
use chat_service::schema::escambiadb::user_info::dsl::*;

#[derive(Clone)]
pub struct Context {
    pub db: PgPool,
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "actix_web=debug,debug");
    env_logger::init();

    let pool = establish_connection();

    HttpServer::new(move || {
        App::new()
            .wrap(Logger::default())
            .wrap(Logger::new("%a %{User-Agent}i"))
            .service(index)
            .app_data(Data::new(Context {
                db: pool.clone()
            }))
    })
        .bind(("0.0.0.0", 8081))?
        .run()
        .await
}

#[get("/")]
async fn index(db_pool: Data<Context>) -> impl Responder {
    let users = user_info
        .order(user_id.asc())
        .load::<UserInfo>(&db_pool.db.get().unwrap())
        .expect("Error loading posts");

    let mut body = String::new();
    users.iter().for_each(|user| {
        body += user.user_id.to_string().as_str();
        body += ", ";
        body += user.user_name.as_str();
        body += ";\n";
    });

    HttpResponse::Ok().body(body)
}
