package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.model.response.CityExchangeCount;
import com.escambia.official.webservice.model.response.TownExchangeCount;
import reactor.core.publisher.Flux;

/**
 * HomeService
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

public interface HomeService {

    Flux<Inventory> getExchangeList(Integer dictionaryId, Boolean isExpireAllowed);

    Flux<CityExchangeCount> getCityExchangeCount(Boolean isExpireAllowed);

    Flux<TownExchangeCount> getTownExchangeCount(Integer cityDictionaryId, Boolean isExpireAllowed);

}
