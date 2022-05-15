package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.Exchange;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

@Repository
@Table("exchange")
public interface ExchangeRepository extends R2dbcRepository<Exchange, Integer> {

}
