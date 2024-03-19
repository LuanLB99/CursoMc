package com.curso.cursomc.config;

import com.curso.cursomc.domain.PaymentWithCard;
import com.curso.cursomc.domain.PaymentWithTicket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PaymentWithCard.class);
                objectMapper.registerSubtypes(PaymentWithTicket.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}
