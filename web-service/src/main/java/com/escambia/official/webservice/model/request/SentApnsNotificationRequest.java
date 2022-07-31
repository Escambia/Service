package com.escambia.official.webservice.model.request;

import lombok.Data;

@Data
public class SentApnsNotificationRequest {

    private Integer sentUserId;

    private Integer receiveUserId;

    private String message;

}
