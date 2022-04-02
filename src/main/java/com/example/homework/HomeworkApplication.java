package com.example.homework;

import com.example.homework.product.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class HomeworkApplication{

	@Autowired
	private ProductRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}
}
