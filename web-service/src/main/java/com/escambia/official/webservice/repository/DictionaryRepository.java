package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.Dictionary;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Table("dictionary")
public interface DictionaryRepository extends R2dbcRepository<Dictionary, Integer> {

    Flux<Dictionary> findAllByType(Integer type);

}
