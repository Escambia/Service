package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.ChatRoom;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

@Repository
@Table("chat_room")
public interface ChatRoomRepository extends R2dbcRepository<ChatRoom, Integer> {
}
