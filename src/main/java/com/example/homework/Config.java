package com.example.homework;

import com.example.homework.helpers.Helper;

/* import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font; */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    
    @Bean
    public Helper helper(){
        return new Helper();
    }

/*
    @Bean
    public Document document(){
        return new Document();
    }

    @Bean
    public Font font(){
        return new Font();
    }

    @Bean
    public Chunk chunk(){
        return new Chunk();
    }
*/
}
