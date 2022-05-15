package com.escambia.official.webservice.model.request;

import lombok.Data;

@Data
public class AddMessageRequest {

    private String content;

    private Integer toUserId;

}
