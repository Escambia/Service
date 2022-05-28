package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.postgresql.Exchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ExchangeService
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

public interface ExchangeService {

    Mono<Exchange> startupExchange(Integer userId, Integer inventoryId, Integer exchangeQuantity);

    Flux<Exchange> getExchangeList(Integer userId);

    Flux<Exchange> getExchangeList(Integer userId, Integer inventoryId);

}
