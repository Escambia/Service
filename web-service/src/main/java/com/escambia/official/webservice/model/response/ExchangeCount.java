package com.escambia.official.webservice.model.response;

public record ExchangeCount(
        Integer cityDictionaryId,
        Integer townDictionaryId,
        Integer count
) {
}
