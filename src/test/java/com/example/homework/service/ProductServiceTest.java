package com.example.homework.service;

import com.example.homework.model.Product;
import com.example.homework.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;


@DataJpaTest
@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @BeforeEach
    void set(){
        service = new ProductService(repository);
    }

    @Test
    void getProducts() {

        Mockito.when(repository.findAll()).thenReturn(getProductList());

        service.getProducts();

        Mockito.verify(repository, Mockito.times(1)).findAll();

    }

    List<Product> getProductList(){
        return Arrays.asList(
                new Product("phone","i am phone",1000),
                new Product("watch","i am watch",100),
                new Product("furniture","i am furniture",3100),
                new Product("cloths","i am cloths",800)
        );
    }
}