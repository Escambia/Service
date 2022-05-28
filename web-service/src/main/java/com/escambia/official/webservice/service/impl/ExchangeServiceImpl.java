package com.escambia.official.webservice.service.impl;

import com.escambia.official.webservice.model.exception.CustomConflictException;
import com.escambia.official.webservice.model.exception.CustomForbiddenException;
import com.escambia.official.webservice.model.exception.CustomNotFoundException;
import com.escambia.official.webservice.model.postgresql.Exchange;
import com.escambia.official.webservice.repository.ExchangeRepository;
import com.escambia.official.webservice.repository.InventoryRepository;
import com.escambia.official.webservice.repository.UserInfoRepository;
import com.escambia.official.webservice.service.ExchangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ExchangeServiceImpl
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@Service
@Transactional(rollbackFor = {Throwable.class, Exception.class})
public class ExchangeServiceImpl implements ExchangeService {

    private final UserInfoRepository userInfoRepository;

    private final InventoryRepository inventoryRepository;

    private final ExchangeRepository exchangeRepository;

    public ExchangeServiceImpl(UserInfoRepository userInfoRepository, InventoryRepository inventoryRepository, ExchangeRepository exchangeRepository) {
        this.userInfoRepository = userInfoRepository;
        this.inventoryRepository = inventoryRepository;
        this.exchangeRepository = exchangeRepository;
    }

    @Override
    public Mono<Exchange> startupExchange(Integer userId, Integer inventoryId, Integer exchangeQuantity) {
        // 1. 確認使用者狀態（未被停用）
        return userInfoRepository.findById(userId)
                .filter(userInfo -> userInfo.getStatus() == 2)
                .flatMap(userInfo -> {
                    // 2. 確認存貨狀態
                    return inventoryRepository.findById(inventoryId)
                            .switchIfEmpty(Mono.error(new CustomConflictException("存貨不存在")))
                            .filter(inventory -> inventory.getStatus() == 2)
                            .switchIfEmpty(Mono.error(new CustomConflictException("存貨已下架")))
                            .filter(inventory -> inventory.getCurrentAmount() >= exchangeQuantity)
                            .switchIfEmpty(Mono.error(new CustomConflictException("存貨數量不足")))
                            .flatMap(inventory -> {
                                // 3. 更新交換數量
                                inventory.setCurrentAmount(inventory.getCurrentAmount() - 1);
                                return inventoryRepository.save(inventory)
                                        .then(exchangeRepository.save(new Exchange(
                                                null,
                                                inventoryId,
                                                userId,
                                                1,
                                                Timestamp.valueOf(LocalDateTime.now()),
                                                null,
                                                null)
                                        ));
                            });
                })
                .switchIfEmpty(Mono.error(new CustomForbiddenException("此帳號未通過驗證或已被停用")));
    }

    @Override
    public Flux<Exchange> getExchangeList(Integer userId) {
        return exchangeRepository.findAllByExchangerUserId(userId)
                .switchIfEmpty(Mono.error(new CustomNotFoundException("查無交換紀錄")));
    }

    @Override
    public Flux<Exchange> getExchangeList(Integer userId, Integer inventoryId) {
        return inventoryRepository.findById(userId)
                .filter(inventory -> Objects.equals(inventory.getInventoryId(), inventoryId))
                .flatMapMany(inventory -> exchangeRepository.findAllByInventoryId(inventoryId));
    }
}
