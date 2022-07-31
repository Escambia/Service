use crate::router::model::{AddChatRoomRequest, UpdateChatRoomRequest};
use anyhow::{anyhow, Result};
use serde::Serialize;
use sqlx::PgPool;

#[derive(Serialize)]
pub struct ChatRoom {
    pub chat_room_id: i32,
    pub host_user_id: i32,
    pub user_id_list: Vec<i32>,
    pub status: i32,
    pub creation_date: chrono::NaiveDateTime,
    pub inventory_id: Option<i32>,
}

impl ChatRoom {
    pub async fn get_chat_room_info(pool: &PgPool, id: i32) -> Result<ChatRoom> {
        let result = sqlx::query!(
            "select * from escambiadb.chat_room where chat_room_id = $1",
            id
        )
        .fetch_optional(pool)
        .await?;
        match result {
            Some(query_result) => Ok(ChatRoom {
                chat_room_id: query_result.chat_room_id,
                host_user_id: query_result.host_user_id,
                user_id_list: query_result.user_id_list,
                status: query_result.status,
                creation_date: query_result.creation_date,
                inventory_id: query_result.inventory_id,
            }),
            _ => Err(anyhow!("Chat room not found.")),
        }
    }

    pub async fn create_chat_room(
        pool: &PgPool,
        parameter: AddChatRoomRequest,
    ) -> Result<ChatRoom> {
        let result = sqlx::query!("insert into escambiadb.chat_room (host_user_id, user_id_list, creation_date, inventory_id) values ($1, $2, $3, $4) returning *",
            parameter.host_user_id,
            &parameter.user_id_list,
            chrono::Local::now().naive_local(),
            parameter.inventory_id
        )
            .fetch_optional(pool)
            .await?;
        match result {
            Some(insert_result) => Ok(ChatRoom {
                chat_room_id: insert_result.chat_room_id,
                host_user_id: insert_result.host_user_id,
                user_id_list: insert_result.user_id_list,
                status: insert_result.status,
                creation_date: insert_result.creation_date,
                inventory_id: insert_result.inventory_id,
            }),
            _ => Err(anyhow!("Chat room not inserted.")),
        }
    }

    pub async fn update_chat_room(
        pool: &PgPool,
        parameter: UpdateChatRoomRequest,
    ) -> Result<ChatRoom> {
        let result = sqlx::query!("update escambiadb.chat_room set user_id_list = $1, status = $2 where chat_room_id = $3 returning *",
            &parameter.user_id_list,
            parameter.status,
            parameter.chat_room_id
        )
            .fetch_optional(pool)
            .await?;
        match result {
            Some(insert_result) => Ok(ChatRoom {
                chat_room_id: insert_result.chat_room_id,
                host_user_id: insert_result.host_user_id,
                user_id_list: insert_result.user_id_list,
                status: insert_result.status,
                creation_date: insert_result.creation_date,
                inventory_id: insert_result.inventory_id,
            }),
            _ => Err(anyhow!("Chat room not updated.")),
        }
    }

    pub async fn get_user_id_list(pool: &PgPool, chat_room_id: i32) -> Result<Vec<i32>> {
        let result = sqlx::query!(
            "(select host_user_id user_id from escambiadb.chat_room where chat_room_id = $1) union all (select unnest(user_id_list) user_id from escambiadb.chat_room where chat_room_id = $1)",
            chat_room_id
        )
        .fetch_all(pool)
        .await?;

        let mut user_id_list: Vec<i32> = Vec::new();

        result.iter().for_each(|row| {
            user_id_list.push(row.user_id.unwrap());
        });

        Ok(user_id_list)
    }
}
