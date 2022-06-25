package com.escambia.official.messageservice;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableMongoRepositories
@EnableReactiveMongoAuditing
@RestController
public class MessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

    @RequestMapping
    @Operation(hidden = true)
    public String home() {
        return """
                                _____                        _     _                     \s
                               |  ___|                      | |   (_)                    \s
                               | |__ ___  ___ __ _ _ __ ___ | |__  _  __ _               \s
                               |  __/ __|/ __/ _` | '_ ` _ \\| '_ \\| |/ _` |              \s
                               | |__\\__ \\ (_| (_| | | | | | | |_) | | (_| |              \s
                               \\____/___/\\___\\__,_|_| |_| |_|_.__/|_|\\__,_|              \s
                                                                                         \s
                                                                                         \s
                ___  ___                                 _____                 _         \s
                |  \\/  |                                /  ___|               (_)        \s
                | .  . | ___  ___ ___  __ _  __ _  ___  \\ `--.  ___ _ ____   ___  ___ ___\s
                | |\\/| |/ _ \\/ __/ __|/ _` |/ _` |/ _ \\  `--. \\/ _ \\ '__\\ \\ / / |/ __/ _ \\
                | |  | |  __/\\__ \\__ \\ (_| | (_| |  __/ /\\__/ /  __/ |   \\ V /| | (_|  __/
                \\_|  |_/\\___||___/___/\\__,_|\\__, |\\___| \\____/ \\___|_|    \\_/ |_|\\___\\___|
                                             __/ |                                       \s
                                            |___/                                        \s
                """;
    }
    
}
