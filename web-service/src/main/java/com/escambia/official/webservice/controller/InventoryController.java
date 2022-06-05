package com.escambia.official.webservice.controller;

import com.escambia.official.webservice.model.UserDto;
import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * InventoryController
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@RestController
@Tag(name = "存貨")
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(summary = "取得存貨資訊")
    @GetMapping("/{inventoryId}")
    public Mono<Inventory> getInventoryInfo(@Parameter(description = "存貨資料庫編號") @PathVariable("inventoryId") Integer inventoryId) {
        return inventoryService.getInventoryInfo(inventoryId);
    }

    @Operation(summary = "更新存貨資訊", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/update")
    public Mono<Inventory> updateInventoryInfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @Parameter(description = "存貨物件") @RequestBody Inventory inventory) {
        return inventoryService.updateInventoryInfo(userDto.userId(), inventory);
    }

    @Operation(summary = "新增存貨資訊", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/add")
    public Mono<Inventory> addInventoryInfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDto userDto, @Parameter(description = "存貨物件") @RequestBody Inventory inventory) {
        return inventoryService.addInventoryInfo(userDto.userId(), inventory);
    }

    @Operation(summary = "取得該用戶所有存貨資訊")
    @GetMapping("/getAllExchangeableInventory")
    public Flux<Inventory> getAllExchangeableInventory(@Parameter(description = "欲查詢的使用者系統編號") Integer userId, @Parameter(description = "交易種類\n1. 交換\n2. 贈送") Integer exchangeType) {
        return inventoryService.getAllExchangeableInventory(userId, exchangeType);
    }

}
