package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.Inventory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

@Repository
@Table("inventory")
public interface InventoryRepository extends R2dbcRepository<Inventory, Integer> {

}
