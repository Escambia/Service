package com.escambia.official.webservice.model.postgresql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table("chat_room")
public class ChatRoom {

    @Id
    @Column("chat_room_id")
    private Integer chatRoomId;

    @Column("host_user_id")
    private Integer hostUserId;

    @Column("user_id_list")
    private List<Integer> userIdList;

    private Integer status;

    @Column("creation_date")
    private LocalDateTime creationDate;

    @Column("inventory_id")
    private Integer inventoryId;

}
