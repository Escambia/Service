use actix_web::{HttpResponse, Responder};
use actix_web::web::{Data, Json};
use diesel::{PgConnection, QueryResult, RunQueryDsl};
use crate::{Context};
use serde::{Deserialize};
use crate::models::ChatRoom;
use crate::schema::escambiadb::chat_room;

#[derive(Deserialize)]
pub struct ChatRoomRequest {
    host_user_id: i32,
    user_id_list: Vec<i32>,
}


pub async fn create_chat_room(pool: Data<Context>, request: Json<ChatRoomRequest>) -> impl Responder {
    let conn = pool.db.get().unwrap();
    insert_chat_room(ChatRoom {
        chat_room_id: None,
        host_user_id: request.host_user_id.clone(),
        user_id_list: request.user_id_list.clone(),
        status: None,
        creation_date: chrono::NaiveDateTime::from_timestamp(chrono::Utc::now().timestamp(), 0),
    }, &conn).expect("Failed to insert chat room");
    HttpResponse::Ok().body("")
}


fn insert_chat_room(object: ChatRoom, conn: &PgConnection) -> QueryResult<()> {
    let _ = diesel::insert_into(chat_room::table)
        .values(object)
        .execute(conn)?;
    Ok(())
}
