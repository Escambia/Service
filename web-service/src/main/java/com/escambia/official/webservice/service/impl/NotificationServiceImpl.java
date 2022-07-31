package com.escambia.official.webservice.service.impl;

import com.eatthepath.pushy.apns.util.InterruptionLevel;
import com.escambia.official.webservice.model.request.SentApnsNotificationRequest;
import com.escambia.official.webservice.repository.UserInfoRepository;
import com.escambia.official.webservice.service.NotificationService;
import com.escambia.official.webservice.utility.ApnsUtility;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final ApnsUtility apnsUtility;
    private final UserInfoRepository userInfoRepository;

    public NotificationServiceImpl(ApnsUtility apnsUtility, UserInfoRepository userInfoRepository) {
        this.apnsUtility = apnsUtility;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Mono<Tuple2<Integer, String>> notificationTest(String apnsToken) {
        return Mono.fromCallable(() -> apnsUtility.createNotification("CINCPAC headquarters", "TURKEY TROTS TO WATER GG FROM CINCPAC ACTION COM THIRD FLEET INFO COMINCH CTF SEVENTY-SEVEN X WHERE IS RPT WHERE IS TASK FORCE THIRTY FOUR RR THE WORLD WONDERS", InterruptionLevel.CRITICAL, apnsToken))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(apnsUtility::sentNotification)
                .map(response -> Tuples.of(
                        response.getStatusCode(),
                        response.getRejectionReason().isPresent() ? response.getRejectionReason().get() : "Notification sent successfully.")
                );
    }

    @Override
    public Mono<Void> chatNotification(SentApnsNotificationRequest request) {
        return Mono.zip(
                        userInfoRepository.findById(request.getSentUserId()),
                        userInfoRepository.findById(request.getReceiveUserId())
                )
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tuple -> {
                    String sentUserName = tuple.getT1().getUserName();
                    String message = request.getMessage();
                    return Mono.fromCallable(() -> apnsUtility.createNotification(sentUserName, message, InterruptionLevel.CRITICAL, tuple.getT2().getApnToken()))
                            .publishOn(Schedulers.boundedElastic())
                            .flatMap(apnsUtility::sentNotification).then();
                });
    }
}
