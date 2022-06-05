package com.escambia.official.webservice.controller;

import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.model.postgresql.Exchange;
import com.escambia.official.webservice.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ExchangeController
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@RestController
@Tag(name = "交換")
@RequestMapping("/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Operation(summary = "啓動交換流程", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/startup")
    public Mono<Exchange> startupExchange(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @Parameter(description = "交換存貨編號") Integer inventoryId, @Parameter(description = "交換數量") Integer exchangeQuantity) {
        return exchangeService.startupExchange(userDto.userId(), inventoryId, exchangeQuantity);
    }

    @Operation(summary = "查詢交換列表（交換者）", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/getExchangeList/requester")
    public Flux<Exchange> getExchangeList(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto) {
        return exchangeService.getExchangeList(userDto.userId());
    }

    @Operation(summary = "查詢交換列表（被交換者）", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/getExchangeList/exchanger")
    public Flux<Exchange> getExchangeList(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @Parameter(description = "交換存貨編號") Integer inventoryId) {
        return exchangeService.getExchangeList(userDto.userId(), inventoryId);
    }
}
