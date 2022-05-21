package com.escambia.official.webservice.controller;

import com.escambia.official.webservice.model.postgresql.Inventory;
import com.escambia.official.webservice.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @Operation(summary = "取得交換物品列表（以地區分別）")
    @GetMapping("/")
    public Flux<Inventory> getExchangeList(@Parameter(description = "字典資料庫編號（僅接受status = 2的縣市）") @RequestParam Integer dictionaryId,
                                           @Parameter(description = "是否允許過期") @RequestParam Boolean isExpireAllowed) {
        return homeService.getExchangeList(dictionaryId, isExpireAllowed);
    }

}
