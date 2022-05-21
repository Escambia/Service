package com.escambia.official.webservice.service.impl;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.repository.InventoryRepository;
import com.escambia.official.webservice.service.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

/**
 * InventoryServiceImpl
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Service
@Transactional(rollbackFor = {Throwable.class, Exception.class})
public class HomeServiceImpl implements HomeService {

    private final InventoryRepository inventoryRepository;

    public HomeServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Flux<Inventory> getExchangeList(Integer dictionaryId, Boolean isExpireAllowed) {
        if (isExpireAllowed) {
            return inventoryRepository.findAllByCityDictionaryIdAndCurrentAmountIsGreaterThan(dictionaryId, 0);
        } else {
            return inventoryRepository.findAllByCityDictionaryIdAndExpiryDateBeforeAndCurrentAmountIsGreaterThan(dictionaryId, LocalDate.now(), 0);
        }
    }
}
