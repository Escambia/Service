package com.escambia.official.webservice;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebServiceApplication
 *
 * @author Ming Chang (<a href="mailto:mail@mingchang.tw">mail@mingchang.tw</a>)
 */

@SpringBootApplication
@EnableR2dbcRepositories
@RestController
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

    @RequestMapping
    @Operation(hidden = true)
    public String home() {
        return """
                      _____                        _     _
                      |  ___|                      | |   (_)
                      | |__ ___  ___ __ _ _ __ ___ | |__  _  __ _
                      |  __/ __|/ __/ _` | '_ ` _ \\| '_ \\| |/ _` |
                      | |__\\__ \\ (_| (_| | | | | | | |_) | | (_| |
                      \\____/___/\\___\\__,_|_| |_| |_|_.__/|_|\\__,_|
                  
                  
                   _    _      _     _____                 _
                  | |  | |    | |   /  ___|               (_)
                  | |  | | ___| |__ \\ `--.  ___ _ ____   ___  ___ ___
                  | |/\\| |/ _ \\ '_ \\ `--. \\/ _ \\ '__\\ \\ / / |/ __/ _ \\
                  \\  /\\  /  __/ |_) /\\__/ /  __/ |   \\ V /| | (_|  __/
                   \\/  \\/ \\___|_.__/\\____/ \\___|_|    \\_/ |_|\\___\\___|
                  
                """;
    }

}

