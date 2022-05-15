package com.escambia.official.webservice.model.postgresql;

import lombok.Data;

@Data
public class ExchangeUsers {

    private Integer exchangeId;

    private Integer userId;

    private Integer exchangerUserId;

}
