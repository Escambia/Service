package com.escambia.official.webservice.model.response;

public record TownExchangeCount(
        Integer cityDictionaryId,
        Integer townDictionaryId,
        Integer count
) {
}
