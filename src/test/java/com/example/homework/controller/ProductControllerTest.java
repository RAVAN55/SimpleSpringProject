package com.example.homework.controller;

import com.example.homework.model.Product;
import com.example.homework.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController controller;

    @Mock
    private ProductService service;

    @BeforeEach
    void set(){
        controller = new ProductController(service);
    }

    @Test
    void getProducts() {

        Mockito.when(service.getProducts()).thenReturn(getProductsList());

        controller.getProducts();

        Mockito.verify(service,Mockito.times(1)).getProducts();
    }


    List<Product> getProductsList(){
       return Arrays.asList(new Product("computer", "i am computer", 150),
        new Product("phone", "i am phone", 200),
        new Product("watch", "i am watch", 50),
        new Product("ring", "i am ring", 80));
    }
}