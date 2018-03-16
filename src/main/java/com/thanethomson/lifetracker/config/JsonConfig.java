package com.thanethomson.lifetracker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
