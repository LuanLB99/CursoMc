package com.curso.cursomc.config;

import com.curso.cursomc.services.DBService;
import com.curso.cursomc.services.EmailService;
import com.curso.cursomc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;
    @Autowired
    private DBService dbService;


    @Bean
    public boolean instantiateDataBase() throws ParseException {
        if(!"create".equals(strategy)){
            return false;
        }

        dbService.instantiateDataBase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
