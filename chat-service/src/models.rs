use crate::schema::escambiadb::chat_room;

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

#[derive(Queryable, Insertable)]
#[table_name = "chat_room"]
pub struct ChatRoom {
    pub chat_room_id: Option<i32>,
    pub host_user_id: i32,
    pub user_id_list: Vec<i32>,
    pub status: Option<i32>,
    pub creation_date: chrono::NaiveDateTime,
}

