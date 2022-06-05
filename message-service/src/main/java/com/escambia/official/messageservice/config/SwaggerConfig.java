package com.escambia.official.messageservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

@OpenAPIDefinition(info = @Info(title = "Escambia 聊天室服務", version = "v1", description = "<a href='https://github.com/Escambia/Service'>GitHub Repository</a>"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("!dev")
    public OpenAPI customConfiguration() {
        return new OpenAPI()
                .servers(Collections
                        .singletonList(new Server().url("https://web.mingchang.tw/escambia/main/")))
                .components(new Components());
    }

}
