package com.animalia.spring.configuacion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentación de la API REST")
                        .version("1.0")
                        .description("Esta es la documentación de la API REST usando Swagger y OpenAPI"));
    }
}