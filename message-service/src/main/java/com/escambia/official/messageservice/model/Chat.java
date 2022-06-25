package com.escambia.official.messageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    private String id;

    private Integer chatRoomId;

    private Integer fromUserId;

    private Integer toUserId;

    private String message;

    private LocalDateTime messageTime;

    private Integer status;


}