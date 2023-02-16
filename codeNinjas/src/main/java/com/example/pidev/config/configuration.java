package com.example.pidev.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class configuration {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(infoAPI());
    }

    public Info infoAPI() {
        return new Info().title("Mobility International").description("Mobility International").contact(contactAPI());
    }

    public Contact contactAPI() {
        Contact contact = new Contact().name("zaineb bachouch").email("zaineb.bachouch@esprit.tn").url("");
        return contact;
    }
}



