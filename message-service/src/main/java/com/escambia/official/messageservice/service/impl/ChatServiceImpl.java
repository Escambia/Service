package com.escambia.official.messageservice.service.impl;

import com.eatthepath.pushy.apns.util.InterruptionLevel;
import com.escambia.official.messageservice.model.Chat;
import com.escambia.official.messageservice.repository.ChatRepository;
import com.escambia.official.messageservice.service.ChatService;
import com.escambia.official.messageservice.utility.ApnsUtility;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ReactiveMongoOperations mongoOperations;

    private final ApnsUtility apnsUtility;

    public ChatServiceImpl(ChatRepository chatRepository, ReactiveMongoOperations mongoOperations, ApnsUtility apnsUtility) {
        this.chatRepository = chatRepository;
        this.mongoOperations = mongoOperations;
        this.apnsUtility = apnsUtility;
    }

    @Override
    public Flux<Chat> getAllChat(Integer chatRoomId) {
        return chatRepository.findAllByChatRoomId(chatRoomId);
    }

    @Override
    public Mono<Chat> sendChat(Chat chat, String apnsToken) {
        return Mono.just(chat)
                .flatMap(chatContent -> {
                    chatContent.setMessageTime(LocalDateTime.now());
                    chatContent.setStatus(1);
                    return chatRepository.save(chatContent);
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(chatInserted -> Mono.fromCallable(() -> apnsUtility.createNotification(
                                        chatInserted.getChatRoomTitle(),
                                        chatInserted.getMessage(),
                                        InterruptionLevel.ACTIVE,
                                        apnsToken
                                ))
                                .publishOn(Schedulers.boundedElastic())
                                .flatMap(apnsUtility::sentNotification)
                                .subscribe()
                );
    }

    @Override
    public Flux<Chat> openStream(Integer chatRoomId, Integer userId) {
        return mongoOperations
                .changeStream("chat",
                        ChangeStreamOptions.builder()
                                .filter(
                                        newAggregation(
                                                match(
                                                        where("operationType").is("insert")
                                                )
                                        )
                                )
                                .build(),
                        Chat.class)
                .filter(chatChangeStreamEvent -> Objects.requireNonNull(chatChangeStreamEvent.getBody()).getChatRoomId().equals(chatRoomId)
                        && (chatChangeStreamEvent.getBody().getToUserId().equals(userId)
                        || chatChangeStreamEvent.getBody().getFromUserId().equals(userId))
                )
                .mapNotNull(ChangeStreamEvent::getBody);
    }
}
