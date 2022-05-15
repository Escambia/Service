package com.escambia.official.webservice.utility;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.*;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class ApnsUtility {

    private static final String APP_BUNDLE_ID = "com.official.PushNotisfication";

    public SimpleApnsPushNotification createNotification(String title, String body, InterruptionLevel interruptionLevel, String apnToken) {
        ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder.setAlertBody(body);
        payloadBuilder.setAlertTitle(title);
        payloadBuilder.setInterruptionLevel(interruptionLevel);
        String payload = payloadBuilder.build();
        String token = TokenUtil.sanitizeTokenString(apnToken);
        return new SimpleApnsPushNotification(token, APP_BUNDLE_ID, payload);
    }

    public void sentNotification(SimpleApnsPushNotification pushNotification) {
        try {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
            ApnsClient apnsClient = new ApnsClientBuilder().setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
                    .setSigningKey(ApnsSigningKey.loadFromInputStream(Objects.requireNonNull(ApnsUtility.class.getClassLoader().getResourceAsStream("APN_Key.p8")),
                            "34926K97KP", "3W2GA43XF8"))
                    .setEventLoopGroup(eventLoopGroup).build();
            PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> sendNotificationFuture = apnsClient.sendNotification(pushNotification);
            PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse = sendNotificationFuture.get();
            if (pushNotificationResponse.isAccepted()) {
                log.info("[ApnsUtility] Push notification accepted by APNs gateway.");
            } else {
                log.error("[ApnsUtility] Notification rejected by the APNs gateway: " + pushNotificationResponse.getRejectionReason());
            }
        } catch (Exception e) {
            log.error("[ApnsUtility] Error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
