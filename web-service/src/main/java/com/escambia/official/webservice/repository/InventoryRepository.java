package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.Inventory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
@Table("inventory")
public interface InventoryRepository extends R2dbcRepository<Inventory, Integer> {

    Flux<Inventory> findAllByCityDictionaryIdAndExpiryDateBeforeAndCurrentAmountIsGreaterThan(Integer cityDictionaryId, LocalDate expiryDate, Integer currentAmount);

    Flux<Inventory> findAllByCityDictionaryIdAndCurrentAmountIsGreaterThan(Integer cityDictionaryId, Integer currentAmount);

}
