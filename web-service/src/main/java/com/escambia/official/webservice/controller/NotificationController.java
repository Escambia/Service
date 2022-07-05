package com.escambia.official.webservice.controller;

import com.eatthepath.pushy.apns.util.InterruptionLevel;
import com.escambia.official.webservice.utility.ApnsUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

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
    public Mono<Tuple2<Integer, String>> notificationTest(@Parameter(description = "Apple Push Notification Token") String apnsToken) {
        return Mono.fromCallable(() -> apnsUtility.createNotification("CINCPAC headquarters", "TURKEY TROTS TO WATER GG FROM CINCPAC ACTION COM THIRD FLEET INFO COMINCH CTF SEVENTY-SEVEN X WHERE IS RPT WHERE IS TASK FORCE THIRTY FOUR RR THE WORLD WONDERS", InterruptionLevel.CRITICAL, apnsToken))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(apnsUtility::sentNotification)
                .map(response -> Tuples.of(
                        response.getStatusCode(),
                        response.getRejectionReason().isPresent() ? response.getRejectionReason().get() : "Notification sent successfully.")
                );
    }
}
