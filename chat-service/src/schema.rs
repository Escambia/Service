pub mod escambiadb {
    table! {
        escambiadb.admin_info (admin_id) {
            admin_id -> Int4,
            account -> Varchar,
            password -> Varchar,
            name -> Varchar,
            role -> Int4,
            status -> Bool,
            creation_date -> Timestamp,
            created_by -> Int4,
            update_date -> Timestamp,
            updated_by -> Int4,
        }
    }

    table! {
        escambiadb.chat_room (chat_room_id) {
            chat_room_id -> Int4,
            host_user_id -> Int4,
            user_id_list -> Array<Int4>,
            status -> Int4,
            creation_date -> Timestamp,
        }
    }

    table! {
        escambiadb.dictionary (dictionary_id) {
            dictionary_id -> Int4,
            #[sql_name = "type"]
            type_ -> Int4,
            name -> Varchar,
            code -> Nullable<Varchar>,
            related_id -> Nullable<Int4>,
        }
    }

    table! {
        escambiadb.exchange (exchange_id) {
            exchange_id -> Int4,
            inventory_id -> Int4,
            exchanger_user_id -> Int4,
            status -> Int4,
            start_date -> Timestamp,
            accept_date -> Nullable<Timestamp>,
            complete_date -> Nullable<Timestamp>,
        }
    }

    table! {
        escambiadb.inventory (inventory_id) {
            inventory_id -> Int4,
            user_id -> Int4,
            name -> Varchar,
            expiry_date -> Nullable<Date>,
            purchase_date -> Nullable<Date>,
            store_method -> Nullable<Varchar>,
            exchange_type -> Nullable<Int4>,
            city_dictionary_id -> Int4,
            town_dictionary_id -> Int4,
            other_description -> Nullable<Text>,
            status -> Int4,
            images -> Array<Varchar>,
            total_amount -> Int4,
            current_amount -> Int4,
        }
    }

    table! {
        escambiadb.user_info (user_id) {
            user_id -> Int4,
            login_type -> Int4,
            google_token -> Nullable<Varchar>,
            apple_token -> Nullable<Varchar>,
            facebook_token -> Nullable<Varchar>,
            user_name -> Varchar,
            real_name -> Varchar,
            email -> Varchar,
            city_dictionary_id -> Int4,
            town_dictionary_id -> Int4,
            address -> Nullable<Varchar>,
            accept_expired -> Bool,
            status -> Int4,
            creation_date -> Timestamp,
            update_date -> Timestamp,
            apn_token -> Nullable<Varchar>,
            user_pic -> Nullable<Text>,
        }
    }

    allow_tables_to_appear_in_same_query!(
        admin_info,
        chat_room,
        dictionary,
        exchange,
        inventory,
        user_info,
    );
}
