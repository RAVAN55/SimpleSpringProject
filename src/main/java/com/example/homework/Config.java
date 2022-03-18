package com.example.homework;

import com.example.homework.helpers.Helper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    
    @Bean
    public Helper helper(){
        return new Helper();
    }

}
