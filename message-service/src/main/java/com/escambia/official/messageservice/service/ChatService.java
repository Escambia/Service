package com.escambia.official.messageservice.service;

import com.escambia.official.messageservice.model.Chat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {

    Flux<Chat> getAllChat(Integer chatRoomId);

    Mono<Chat> sendChat(Chat chat, String apnsToken);

    Flux<Chat> openStream(Integer chatRoomId, Integer userId);

}
