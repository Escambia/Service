package com.escambia.official.webservice.service.impl;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.repository.CustomRepository;
import com.escambia.official.webservice.repository.InventoryRepository;
import com.escambia.official.webservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * InventoryServiceImpl
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Service
@Transactional(rollbackFor = {Throwable.class, Exception.class})
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final CustomRepository customRepository;


    public InventoryServiceImpl(InventoryRepository inventoryRepository, CustomRepository customRepository) {
        this.inventoryRepository = inventoryRepository;
        this.customRepository = customRepository;
    }

    @Override
    public Mono<Inventory> getInventoryInfo(Integer inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }

    @Override
    public Flux<Inventory> getAllExchangeableInventory(Integer userId, Integer exchangeType) {
        return customRepository.getAllExchangeableInventory(userId, exchangeType);
    }

    @Override
    public Mono<Inventory> updateInventoryInfo(Integer userId, Inventory inventory) {
        return inventoryRepository.findById(inventory.getInventoryId()).flatMap(inventoryInDb -> {
            if (inventoryInDb.getUserId().equals(userId)) {
                return inventoryRepository.save(inventory);
            } else {
                return Mono.error(new HttpStatusCodeException(HttpStatus.FORBIDDEN, "您不是本物件的創建者，無權限更改內容") {
                });
            }
        });
    }

    @Override
    public Mono<Inventory> addInventoryInfo(Integer userId, Inventory inventory) {
        inventory.setUserId(userId);
        return inventoryRepository.save(inventory);
    }
}
