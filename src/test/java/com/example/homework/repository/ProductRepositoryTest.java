package com.example.homework.repository;

import com.example.homework.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @AfterEach
    void deleteAll(){
        repository.deleteAll();
    }

    @Test
    void findByName() {

        Product product = new Product("mouse","i am mouse",200);
        Product productNew = new Product("keyboard","i am keyboard",800);
        repository.save(product);
        repository.save(productNew);

        Product result = repository.findByName(product.getProductName());

        assertThat(result).isEqualTo(product);
    }


    @Test
    void ifFindByNameNotExist(){

        Product result = repository.findByName("mouse");

        assertThat(result).isNull();
    }
}