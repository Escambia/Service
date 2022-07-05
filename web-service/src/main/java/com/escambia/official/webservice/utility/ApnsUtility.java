package com.escambia.official.webservice.utility;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.InterruptionLevel;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Component
public class ApnsUtility {

    private static final String APP_BUNDLE_ID = "com.official.PushNotisfication";

    public SimpleApnsPushNotification createNotification(String title, String body, InterruptionLevel interruptionLevel, String apnToken) {
        var payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder.setAlertBody(body);
        payloadBuilder.setAlertTitle(title);
        payloadBuilder.setInterruptionLevel(interruptionLevel);
        return new SimpleApnsPushNotification(
                TokenUtil.sanitizeTokenString(apnToken),
                APP_BUNDLE_ID,
                payloadBuilder.build()
        );
    }

    public Mono<PushNotificationResponse<SimpleApnsPushNotification>> sentNotification(SimpleApnsPushNotification pushNotification) {
        return Mono.fromFuture(() -> {
            var eventLoopGroup = new NioEventLoopGroup(4);
            ApnsClient apnsClient;
            try {
                apnsClient = new ApnsClientBuilder().setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
                        .setSigningKey(ApnsSigningKey.loadFromInputStream(Objects.requireNonNull(ApnsUtility.class.getClassLoader().getResourceAsStream("APN_Key.p8")),
                                "34926K97KP", "3W2GA43XF8"))
                        .setEventLoopGroup(eventLoopGroup).build();
            } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
            return apnsClient.sendNotification(pushNotification);
        });
    }
}
