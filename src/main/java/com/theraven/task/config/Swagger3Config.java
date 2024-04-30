package com.theraven.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Test Task")
                        .description("API.")
                        .version("1.0").contact(new Contact()
                                .name("Dmytro Markevych")
                                .email("markevychdmytro05@gmail.com")
                                .url("https://www.linkedin.com/in/dmytro-markevych/")
                        )
                );
    }
}
