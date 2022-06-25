package com.escambia.official.webservice.service.impl;

import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.model.postgresql.UserInfo;
import com.escambia.official.webservice.model.request.LoginRequest;
import com.escambia.official.webservice.model.request.RegisterRequest;
import com.escambia.official.webservice.repository.UserInfoRepository;
import com.escambia.official.webservice.service.UserService;
import com.escambia.official.webservice.utility.JwtUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = {Throwable.class, Exception.class})
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final JwtUtility jwtUtility;

    public UserServiceImpl(UserInfoRepository userInfoRepository, JwtUtility jwtUtility) {
        this.userInfoRepository = userInfoRepository;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public Mono<String> login(LoginRequest request) {
        return switch (request.getLoginType()) {
            case 1 -> userInfoRepository.findByGoogleToken(request.getGoogleToken()).map(jwtUtility::generateTokenUser);
            case 2 -> userInfoRepository.findByAppleToken(request.getAppleToken()).map(jwtUtility::generateTokenUser);
            case 3 ->
                    userInfoRepository.findByFacebookToken(request.getFacebookToken()).map(jwtUtility::generateTokenUser);
            default -> Mono.error(new Throwable("Missing parameter: loginType."));
        };
    }

    @Override
    public Mono<Void> register(RegisterRequest request) {
        var insertData = new ObjectMapper().convertValue(request, UserInfo.class);
        insertData.setStatus(1);
        insertData.setCreationDate(LocalDateTime.now());
        insertData.setUpdateDate(LocalDateTime.now());
        insertData.setAcceptExpired(false);
        return userInfoRepository.save(insertData)
                .doOnSuccess(userInfoInfo -> {
                    // TODO 寄驗證Email給註冊者
                }).then();
    }

    @Override
    public Mono<Void> updateApnsToken(UserDto userDto, String apnsToken) {
        return userInfoRepository.findById(userDto.userId()).flatMap(userInfoInfo -> {
            userInfoInfo.setApnToken(apnsToken);
            userInfoInfo.setUpdateDate(LocalDateTime.now());
            return userInfoRepository.save(userInfoInfo).then();
        });
    }

    @Override
    public Mono<String> getUserApnsToken(Integer userId) {
        return userInfoRepository.findById(userId)
                .map(UserInfo::getApnToken);
    }
}
