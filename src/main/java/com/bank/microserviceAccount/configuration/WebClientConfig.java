package com.bank.microserviceAccount.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    // Define el WebClient como un bean para el CustomerService
    @Bean
    public WebClient customerWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/customers") // Cambia según la URL del CustomerService
                .build();
    }

    @Bean
    public WebClient customerWebClienttwo() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082/") // Cambia según la URL del CustomerService
                .build();
    }
}
