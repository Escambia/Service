package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.postgresql.Inventory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * InventoryService
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

public interface InventoryService {
    Mono<Inventory> getInventoryInfo(Integer inventoryId);

    Flux<Inventory> getAllExchangeableInventory(Integer userId, Integer exchangeType);

    Mono<Inventory> updateInventoryInfo(Integer userId, Inventory inventory);

    Mono<Inventory> addInventoryInfo(Integer userId, Inventory inventory);
}
