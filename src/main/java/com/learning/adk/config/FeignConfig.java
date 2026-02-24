package com.learning.adk.config;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        // This tells Feign to log the headers, body, and metadata of the external API call
        return Logger.Level.FULL;
    }
}