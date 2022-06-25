package com.escambia.official.messageservice.service.impl;

import com.escambia.official.messageservice.model.Chat;
import com.escambia.official.messageservice.repository.ChatRepository;
import com.escambia.official.messageservice.service.ChatService;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ReactiveMongoOperations mongoOperations;

    public ChatServiceImpl(ChatRepository chatRepository, ReactiveMongoOperations mongoOperations) {
        this.chatRepository = chatRepository;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Flux<Chat> getAllChat(Integer chatRoomId) {
        return chatRepository.findAllByChatRoomId(chatRoomId);
    }

    @Override
    public Mono<Chat> sendChat(Chat chat) {
        return Mono.just(chat)
                .flatMap(chatContent -> {
                    chatContent.setMessageTime(LocalDateTime.now());
                    chatContent.setStatus(1);
                    return chatRepository.save(chatContent);
                });
    }

    @Override
    public Flux<Chat> openStream(Integer chatRoomId, Integer userId) {
        return mongoOperations
                .changeStream("chat",
                        ChangeStreamOptions.builder()
                                .filter(
                                        newAggregation(
                                                match(
                                                        where("operationType").is("insert").and("operationType").is("update")
                                                                .andOperator(
                                                                        where("chatRoomId").is(chatRoomId)
                                                                                .andOperator(
                                                                                        where("fromUserId").is(userId)
                                                                                                .orOperator(
                                                                                                        where("toUserId").is(userId)
                                                                                                )
                                                                                )
                                                                )
                                                )
                                        )
                                )
                                .build(),
                        Chat.class)
                .mapNotNull(ChangeStreamEvent::getBody);
    }
}
