package com.escambia.official.webservice.controller;

import com.eatthepath.pushy.apns.util.InterruptionLevel;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.model.request.LoginRequest;
import com.escambia.official.webservice.model.request.RegisterRequest;
import com.escambia.official.webservice.service.UserService;
import com.escambia.official.webservice.utility.ApnsUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "用戶")
@RestController
@RequestMapping("/user")
public class UserController {

    private final ApnsUtility apnsUtility;

    private final UserService userService;

    public UserController(ApnsUtility apnsUtility, UserService userService) {
        this.apnsUtility = apnsUtility;
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
    public Mono<Void> updateApnsToken(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @Parameter(description = "Apple Push Notification Token") @RequestBody String apnsToken) {
        return userService.updateApnsToken(userDto, apnsToken);
    }

    @Operation(summary = "測試推送通知功能")
    @GetMapping("/notificationTest")
    public Mono<Void> notificationTest(@Parameter(description = "Apple Push Notification Token") @RequestParam String apnsToken) {
        SimpleApnsPushNotification notification = apnsUtility.createNotification("CINCPAC headquarters", "TURKEY TROTS TO WATER GG FROM CINCPAC ACTION COM THIRD FLEET INFO COMINCH CTF SEVENTY-SEVEN X WHERE IS RPT WHERE IS TASK FORCE THIRTY FOUR RR THE WORLD WONDERS", InterruptionLevel.CRITICAL, apnsToken);
        apnsUtility.sentNotification(notification);
        return Mono.empty().then();
    }

}
