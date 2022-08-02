use crate::router::model::{AddChatMessageRequest, GetChatHistoryRequest};
use anyhow::Result;
use serde::Serialize;
use sqlx::PgPool;

#[derive(Serialize)]
pub struct ChatHistory {
    pub chat_history_id: i32,
    pub chat_room_id: i32,
    pub sent_user_id: i32,
    pub message_content: String,
    pub sent_datetime: chrono::NaiveDateTime,
}

impl ChatHistory {
    pub async fn get_chat_history(
        pool: &PgPool,
        parameter: GetChatHistoryRequest,
    ) -> Result<Vec<ChatHistory>> {
        let result = sqlx::query!("select * from escambiadb.chat_history where chat_room_id = $1 and sent_datetime = $2", parameter.chat_room_id, parameter.latest_received_datetime)
            .fetch_all(pool)
            .await?;

        let mut chat_history_list = Vec::new();

        result.iter().for_each(|row| {
            chat_history_list.push(ChatHistory {
                chat_history_id: row.chat_history_id,
                chat_room_id: row.chat_room_id,
                sent_user_id: row.sent_user_id,
                message_content: row.message_content.parse().unwrap(),
                sent_datetime: row.sent_datetime,
            });
        });

        Ok(chat_history_list)
    }

    pub async fn save_chat_history(pool: &PgPool, parameter: AddChatMessageRequest) {
        sqlx::query!("insert into escambiadb.chat_history (chat_room_id, sent_user_id, message_content, sent_datetime) values ($1, $2, $3 ,$4)",
            parameter.chat_room_id,
            parameter.sent_user_id,
            parameter.message_content,
            chrono::Local::now().naive_local()
        )
            .execute(pool)
            .await.expect("Error saving chat history.");
    }
}
