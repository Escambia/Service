package com.escambia.official.webservice.model.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String userName;

    private String realName;

    private String email;

    private Integer cityDictionaryId;

    private Integer townDictionaryId;

    private String address;

    private Integer loginType;

    private String googleToken;

    private String appleToken;

    private String facebookToken;

    private String apnToken;

}
