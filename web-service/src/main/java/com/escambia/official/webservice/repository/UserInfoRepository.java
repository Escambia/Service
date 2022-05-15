package com.escambia.official.webservice.repository;

import com.escambia.official.webservice.model.postgresql.UserInfo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Table("user_info")
public interface UserInfoRepository extends R2dbcRepository<UserInfo, Integer> {

    Mono<UserInfo> findByGoogleToken(String googleToken);

    Mono<UserInfo> findByAppleToken(String appleToken);

    Mono<UserInfo> findByFacebookToken(String facebookToken);

}
