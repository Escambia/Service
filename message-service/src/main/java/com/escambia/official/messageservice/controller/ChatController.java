package com.escambia.official.messageservice.controller;

import com.escambia.official.messageservice.model.Chat;
import com.escambia.official.messageservice.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "聊天室")
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Chat> getAllChat(Integer chatRoomId) {
        return chatService.getAllChat(chatRoomId);
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Chat> sendChat(Chat chat) {
        return chatService.sendChat(chat);
    }

    @GetMapping(value = "/openStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> openStream(Integer chatRoomId, Integer userId) {
        return chatService.openStream(chatRoomId, userId);
    }
}
