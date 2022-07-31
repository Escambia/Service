use serde::Deserialize;

#[derive(Deserialize)]
pub struct GetChatRoomRequest {
    pub id: i32,
}

#[derive(Deserialize)]
pub struct AddChatRoomRequest {
    pub host_user_id: i32,
    pub user_id_list: Vec<i32>,
    pub inventory_id: i32,
}

#[derive(Deserialize)]
pub struct UpdateChatRoomRequest {
    pub chat_room_id: i32,
    pub user_id_list: Vec<i32>,
    pub status: i32,
}

#[derive(Deserialize)]
pub struct GetChatHistoryRequest {
    pub chat_room_id: i32,
    pub chat_history_id: i32,
}

#[derive(Deserialize)]
pub struct AddChatMessageRequest {
    pub chat_room_id: i32,
    pub sent_user_id: i32,
    pub message_content: String,
    pub sent_datetime: chrono::NaiveDateTime,
}
