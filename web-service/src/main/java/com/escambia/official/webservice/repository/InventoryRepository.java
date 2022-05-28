package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.Inventory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
@Table("inventory")
public interface InventoryRepository extends R2dbcRepository<Inventory, Integer> {

    Flux<Inventory> findAllByCityDictionaryIdAndExpiryDateBeforeAndCurrentAmountIsGreaterThan(Integer cityDictionaryId, LocalDate expiryDate, Integer currentAmount);

    Flux<Inventory> findAllByTownDictionaryIdAndCurrentAmountIsGreaterThan(Integer townDictionaryId, Integer currentAmount);

    Mono<Integer> countAllByCityDictionaryId(Integer cityDictionaryId);

    Mono<Integer> countAllByTownDictionaryId(Integer townDictionaryId);

}
