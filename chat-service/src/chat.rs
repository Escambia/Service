use actix_web::{HttpResponse, Responder};
use actix_web::web::{Data, Json};
use diesel::{insert_into, PgConnection, QueryDsl, RunQueryDsl, update};
use serde::{Deserialize};
use utoipa::{Component};
use crate::{Context};
use crate::models::{ChatRoom, NewChatRoom, UpdateChatRoom};
use crate::schema::escambiadb::chat_room;

#[derive(Deserialize, Component)]
pub struct GetChatRoomInfo {
    id: i32,
}

#[derive(Deserialize, Component)]
pub struct AddChatRoomRequest {
    host_user_id: i32,
    user_id_list: Vec<i32>,
    inventory_id: i32,
}

#[derive(Deserialize, Component)]
pub struct UpdateChatRoomRequest {
    chat_room_id: i32,
    user_id_list: Vec<i32>,
    status: i32,
}

#[utoipa::path(post, path = "/getChatRoomInfo", request_body(content_type = "application/json", content = GetChatRoomInfo))]
pub async fn get_chat_room_info(pool: Data<Context>, id: Json<GetChatRoomInfo>) -> impl Responder{
    let connection = pool.db.get().unwrap();
    let data = select_db_chat_room(id.id, &connection);
    HttpResponse::Ok().json(data)
}

#[utoipa::path(post, path = "/createChatRoom", request_body(content_type = "application/json", content = AddChatRoomRequest))]
pub async fn create_chat_room(pool: Data<Context>, request: Json<AddChatRoomRequest>) -> impl Responder {
    let connection = pool.db.get().unwrap();
    let new_chat_room = NewChatRoom {
        host_user_id: request.host_user_id,
        user_id_list: request.user_id_list.clone(),
        creation_date: chrono::Local::now().naive_local(),
        inventory_id: request.inventory_id
    };
    let result = insert_db_chat_room(new_chat_room, &connection);
    HttpResponse::Ok().body(serde_json::to_string(&result).unwrap())
}

#[utoipa::path(post, path = "/updateChatRoom", request_body(content_type = "application/json", content = UpdateChatRoomRequest))]
pub async fn update_chat_room(pool: Data<Context>, request: Json<UpdateChatRoomRequest>) -> impl Responder {
    let connection = pool.db.get().unwrap();
    let edit_chat_room = UpdateChatRoom {
        user_id_list: request.user_id_list.clone(),
        status: request.status.clone(),
    };
    let result = update_db_chat_room(request.chat_room_id, edit_chat_room, &connection);
    HttpResponse::Ok().body(serde_json::to_string(&result).unwrap())
}

fn select_db_chat_room(id: i32, connection: &PgConnection) -> ChatRoom {
    chat_room::table.find(id).get_result::<ChatRoom>(connection).unwrap()
}

fn insert_db_chat_room(object: NewChatRoom, connection: &PgConnection) -> ChatRoom {
    insert_into(chat_room::table)
        .values(object)
        .get_result::<ChatRoom>(connection).unwrap()
}

fn update_db_chat_room(target: i32, object: UpdateChatRoom, connection: &PgConnection) -> ChatRoom {
    let orignal_chat_room = chat_room::table.find(target);
    update(orignal_chat_room)
        .set(object)
        .get_result::<ChatRoom>(connection).unwrap()
}
