package com.shopperskart.order_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // This annotation is used for load balancing with Spring Cloud
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
