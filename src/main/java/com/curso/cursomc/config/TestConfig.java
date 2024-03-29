package com.curso.cursomc.config;

import com.curso.cursomc.services.DBService;
import com.curso.cursomc.services.EmailService;
import com.curso.cursomc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDataBase() throws ParseException {
        dbService.instantiateDataBase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
