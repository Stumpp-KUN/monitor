package com.example.statictics.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String auth = "admin:admin";
                String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
                template.header("Authorization", "Basic " + encodedAuth);
            }
        };
    }
}
