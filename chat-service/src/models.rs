use crate::schema::escambiadb::chat_room;
use serde::Serialize;

#[derive(Queryable)]
pub struct UserInfo {
    pub user_id: i32,
    pub login_type: i32,
    pub google_token: Option<String>,
    pub apple_token: Option<String>,
    pub facebook_token: Option<String>,
    pub user_name: String,
    pub real_name: String,
    pub email: String,
    pub city_dictionary_id: i32,
    pub town_dictionary_id: i32,
    pub address: Option<String>,
    pub accept_expired: bool,
    pub status: i32,
    pub creation_date: chrono::NaiveDateTime,
    pub update_date: chrono::NaiveDateTime,
    pub apn_token: Option<String>,
    pub user_pic: Option<String>,
}

#[derive(Queryable, Identifiable, Serialize)]
#[table_name = "chat_room"]
#[primary_key(chat_room_id)]
pub struct ChatRoom {
    pub chat_room_id: i32,
    pub host_user_id: i32,
    pub user_id_list: Vec<i32>,
    pub status: i32,
    pub creation_date: chrono::NaiveDateTime,
    pub inventory_id: i32,
}

#[derive(Insertable)]
#[table_name = "chat_room"]
pub struct NewChatRoom {
    pub host_user_id: i32,
    pub user_id_list: Vec<i32>,
    pub creation_date: chrono::NaiveDateTime,
    pub inventory_id: i32,
}

#[derive(AsChangeset)]
#[table_name = "chat_room"]
pub struct UpdateChatRoom {
    pub user_id_list: Vec<i32>,
    pub status: i32,
}
