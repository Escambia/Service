package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.postgresql.Inventory;
import reactor.core.publisher.Flux;

/**
 * HomeService
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

public interface HomeService {

    Flux<Inventory> getExchangeList(Integer dictionaryId, Boolean isExpireAllowed);

}
