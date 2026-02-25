package com.learning.adk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    //Bean for Object Mapper
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
