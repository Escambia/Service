package com.escambia.official.webservice.controller;

import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.model.request.LoginRequest;
import com.escambia.official.webservice.model.request.RegisterRequest;
import com.escambia.official.webservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "用戶")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "登入")
    @PostMapping("/login")
    public Mono<String> login(@Parameter(description = "登入資訊") @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @Operation(summary = "註冊")
    @PostMapping("/register")
    public Mono<Void> register(@Parameter(description = "註冊資訊") @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @Operation(summary = "更新Apple Push Notification Token")
    @PatchMapping("/updateApnsToken")
    public Mono<Void> updateApnsToken(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @Parameter(description = "Apple Push Notification Token") String apnsToken) {
        return userService.updateApnsToken(userDto, apnsToken);
    }

}
