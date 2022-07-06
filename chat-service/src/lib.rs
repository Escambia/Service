#[macro_use]
extern crate diesel;

use diesel::pg::PgConnection;
use std::env;
use diesel::r2d2::{ConnectionManager, PoolError};
use r2d2::{Pool};

pub mod schema;
pub mod models;

pub type PgPool = Pool<ConnectionManager<PgConnection>>;

fn init_pool() -> Result<PgPool, PoolError> {
    let manager = ConnectionManager::<PgConnection>::new(
        if env::var("PROFILE").unwrap() == "prod" {
            "postgresql://postgres:postgres@database.c7zj1xnu7zet.us-west-2.rds.amazonaws.com:5432/postgres"
        } else {
            "postgresql://chisakikirino:Ming137137@localhost:5432/postgres"
        }
    );
    Pool::builder().build(manager)
}

pub fn establish_connection() -> PgPool {
    init_pool().expect("Failed to create pool")
}
