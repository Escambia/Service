package com.escambia.official.webservice.service.impl;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.model.response.ExchangeCount;
import com.escambia.official.webservice.repository.DictionaryRepository;
import com.escambia.official.webservice.repository.InventoryRepository;
import com.escambia.official.webservice.service.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

/**
 * InventoryServiceImpl
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Service
@Transactional(rollbackFor = {Throwable.class, Exception.class})
public class HomeServiceImpl implements HomeService {

    private final InventoryRepository inventoryRepository;

    private final DictionaryRepository dictionaryRepository;

    public HomeServiceImpl(InventoryRepository inventoryRepository, DictionaryRepository dictionaryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public Flux<Inventory> getExchangeList(Integer dictionaryId, Boolean isExpireAllowed) {
        if (isExpireAllowed) {
            return inventoryRepository.findAllByCityDictionaryIdAndCurrentAmountIsGreaterThan(dictionaryId, 0);
        } else {
            return inventoryRepository.findAllByCityDictionaryIdAndExpiryDateBeforeAndCurrentAmountIsGreaterThan(dictionaryId, LocalDate.now(), 0);
        }
    }

    @Override
    public Flux<ExchangeCount> getExchangeCount(Boolean isExpireAllowed) {
        return dictionaryRepository.findAllByType(3)
                .flatMap(dictionary -> inventoryRepository.countAllByTownDictionaryId(dictionary.getDictionaryId())
                        .publishOn(Schedulers.boundedElastic())
                        .map(count -> new ExchangeCount(dictionary.getRelatedId(), dictionary.getDictionaryId(), count)));
    }
}
