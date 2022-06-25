package com.escambia.official.messageservice.controller;

import com.escambia.official.messageservice.model.Chat;
import com.escambia.official.messageservice.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @Operation(summary = "查詢所有聊天室記錄")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Chat> getAllChat(Integer chatRoomId) {
        return chatService.getAllChat(chatRoomId);
    }

    @Operation(summary = "傳送訊息")
    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Chat> sendChat(@RequestBody Chat chat, String apnsToken) {
        return chatService.sendChat(chat, apnsToken);
    }

    @Operation(summary = "接收訊息（Server Sent Event）")
    @GetMapping(value = "/openStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> openStream(Integer chatRoomId, Integer userId) {
        return chatService.openStream(chatRoomId, userId);
    }
}
