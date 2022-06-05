package com.escambia.official.messageservice.controller;

import com.eatthepath.pushy.apns.util.InterruptionLevel;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.escambia.official.messageservice.utility.ApnsUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "推播通知")
@RequestMapping("/notification")
public class NotificationController {

    private final ApnsUtility apnsUtility;

    public NotificationController(ApnsUtility apnsUtility) {
        this.apnsUtility = apnsUtility;
    }

    @Operation(summary = "測試推送通知功能")
    @GetMapping("/notificationTest")
    public Mono<Void> notificationTest(@Parameter(description = "Apple Push Notification Token") String apnsToken) {
        SimpleApnsPushNotification notification = apnsUtility.createNotification("CINCPAC headquarters", "TURKEY TROTS TO WATER GG FROM CINCPAC ACTION COM THIRD FLEET INFO COMINCH CTF SEVENTY-SEVEN X WHERE IS RPT WHERE IS TASK FORCE THIRTY FOUR RR THE WORLD WONDERS", InterruptionLevel.CRITICAL, apnsToken);
        apnsUtility.sentNotification(notification);
        return Mono.empty().then();
    }
}
