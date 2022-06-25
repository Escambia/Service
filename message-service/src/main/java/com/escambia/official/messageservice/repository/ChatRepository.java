package com.escambia.official.messageservice.repository;

import com.escambia.official.messageservice.model.Chat;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveCrudRepository<Chat, String> {

    Flux<Chat> findAllByChatRoomId(Integer chatRoomId);

}
