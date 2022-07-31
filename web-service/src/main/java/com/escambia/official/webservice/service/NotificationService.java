package com.escambia.official.webservice.service;

import com.escambia.official.webservice.model.request.SentApnsNotificationRequest;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

public interface NotificationService {

    Mono<Tuple2<Integer, String>> notificationTest(String apnsToken);

    Mono<Void> chatNotification(List<SentApnsNotificationRequest> request);

}
