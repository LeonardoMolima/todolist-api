package com.leonardoexpedito.todosimple.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI().info(new Info()
                .title("To-Do List API")
                .version("1.0.0")
                .description("To-Do List API desenvolvida para fins de estudo."));
    }

}
