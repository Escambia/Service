package com.escambia.official.webservice.repository.impl;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.repository.CustomRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    private final DatabaseClient databaseClient;

    public CustomRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<Inventory> getAllExchangeableInventory(Integer userId, Integer exchangeType) {
        return databaseClient
                .sql("""
                        select *
                        from inventory
                        where exchange_type = :param2
                          and city_dictionary_id = (select city_dictionary_id from user_info where user_id = :param1)
                          and town_dictionary_id = (select town_dictionary_id from user_info where user_id = :param1)
                          and case when (select accept_expired from user_info where user_id = :param1) then status = 2 else status in (2, 3) end
                        """)
                .bind("param1", userId)
                .bind("param2", exchangeType)
                .map(row -> {
                    var inventory = new Inventory();
                    inventory.setInventoryId((Integer) row.get("inventory_id"));
                    inventory.setUserId((Integer) row.get("user_id"));
                    inventory.setName((String) row.get("name"));
                    inventory.setExchangeType((Integer) row.get("exchange_type"));
                    inventory.setCurrentAmount((Integer) row.get("current_amount"));
                    inventory.setImages((String[]) row.get("images"));
                    return inventory;
                })
                .all();
    }

}
