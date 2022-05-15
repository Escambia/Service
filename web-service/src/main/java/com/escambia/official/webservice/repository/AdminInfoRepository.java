package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.AdminInfo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;

@Repository
@Table("admin_info")
public interface AdminInfoRepository extends R2dbcRepository<AdminInfo, Integer> {
}
