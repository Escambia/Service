package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.model.response.ExchangeCount;
import reactor.core.publisher.Flux;

/**
 * HomeService
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

public interface HomeService {

    Flux<Inventory> getExchangeList(Integer dictionaryId, Boolean isExpireAllowed);

    Flux<ExchangeCount> getExchangeCount(Boolean isExpireAllowed);

}
