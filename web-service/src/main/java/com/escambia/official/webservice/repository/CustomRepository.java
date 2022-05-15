package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.Inventory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * CustomRepository
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Repository
public interface CustomRepository {

    Flux<Inventory> getAllExchangeableInventory(Integer userId, Integer exchangeType);

}
