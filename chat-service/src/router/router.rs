use crate::router::model::{
    AddChatRoomRequest, GetChatHistoryRequest, GetChatRoomRequest, UpdateChatRoomRequest,
};
use crate::service::chat_history::ChatHistory;
use crate::service::chat_room::ChatRoom;
use actix_web::web::{Data, Json, Query};
use actix_web::{get, post, put, web, HttpResponse, Responder};
use sqlx::PgPool;

pub fn init(config: &mut web::ServiceConfig) {
    config.service(get_chat_room_info);
    config.service(create_chat_room);
    config.service(update_chat_room);
    config.service(get_chat_history);
}

#[get("/getChatRoomInfo")]
pub async fn get_chat_room_info(
    pool: Data<PgPool>,
    id: Query<GetChatRoomRequest>,
) -> impl Responder {
    let result = ChatRoom::get_chat_room_info(pool.get_ref(), id.id).await;
    match result {
        Ok(chat_room) => HttpResponse::Ok().json(chat_room),
        _ => HttpResponse::NotFound().body("Chat room not found."),
    }
}

#[post("/createChatRoom")]
pub async fn create_chat_room(
    pool: Data<PgPool>,
    request: Json<AddChatRoomRequest>,
) -> impl Responder {
    let result = ChatRoom::create_chat_room(pool.get_ref(), request.into_inner()).await;
    match result {
        Ok(chat_room) => HttpResponse::Ok().json(chat_room),
        _ => HttpResponse::BadRequest()
            .body("Chat room not created, contact the administrator for more information."),
    }
}

#[put("/updateChatRoom")]
pub async fn update_chat_room(
    pool: Data<PgPool>,
    request: Json<UpdateChatRoomRequest>,
) -> impl Responder {
    let result = ChatRoom::update_chat_room(pool.get_ref(), request.into_inner()).await;
    match result {
        Ok(chat_room) => HttpResponse::Ok().json(chat_room),
        _ => HttpResponse::BadRequest()
            .body("Chat room not updated, contact the administrator for more information."),
    }
}

#[get("/getChatHistory")]
pub async fn get_chat_history(
    pool: Data<PgPool>,
    request: Query<GetChatHistoryRequest>,
) -> impl Responder {
    let result = ChatHistory::get_chat_history(pool.get_ref(), request.into_inner()).await;
    match result {
        Ok(chat_history) => HttpResponse::Ok().json(chat_history),
        _ => HttpResponse::NotFound().body("No chat history found."),
    }
}
