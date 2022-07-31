package com.escambia.official.webservice.controller;

import com.escambia.official.webservice.model.request.SentApnsNotificationRequest;
import com.escambia.official.webservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@RestController
@Tag(name = "推播通知")
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "測試推送通知功能")
    @GetMapping("/notificationTest")
    public Mono<Tuple2<Integer, String>> notificationTest(@Parameter(description = "Apple Push Notification Token") String apnsToken) {
        return notificationService.notificationTest(apnsToken);
    }

    @Operation(summary = "發送聊天室推播通知（後端溝通用，App勿用）")
    @PostMapping("/chatNotification")
    public Mono<Void> chatNotification(List<SentApnsNotificationRequest> request) {
        return notificationService.chatNotification(request);
    }
}
