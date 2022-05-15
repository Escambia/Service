package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.model.request.LoginRequest;
import com.escambia.official.webservice.model.request.RegisterRequest;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<String> login(LoginRequest request);

    Mono<Void> register(RegisterRequest request);

    Mono<Void> updateApnsToken(UserDto userDto, String apnsToken);

}
