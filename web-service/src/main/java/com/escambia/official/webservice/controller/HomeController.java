package com.escambia.official.webservice.controller;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.model.response.ExchangeCount;
import com.escambia.official.webservice.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * HomeController
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@RestController
@Tag(name = "首頁")
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @Operation(summary = "取得總交換物品數（以地區分別）")
    @GetMapping("/")
    public Flux<ExchangeCount> getExchangeCount(@Parameter(description = "是否允許過期") @RequestParam Boolean isExpireAllowed) {
        return homeService.getExchangeCount(isExpireAllowed);
    }

    @Operation(summary = "取得地區交換物品列表")
    @GetMapping("/list")
    public Flux<Inventory> getExchangeList(@Parameter(description = "字典資料庫編號（僅接受status = 2的縣市）") @RequestParam Integer dictionaryId,
                                           @Parameter(description = "是否允許過期") @RequestParam Boolean isExpireAllowed) {
        return homeService.getExchangeList(dictionaryId, isExpireAllowed);
    }

}
