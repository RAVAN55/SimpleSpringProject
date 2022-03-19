package com.example.homework.repository;

import com.example.homework.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class CustomerRepositoryTest {

    /*the database used by CustomerRepository here is h2 for that i specially created resources package under test and we */
    /*also provided the application.properties file. so it will pick up the config from newly created application.properties*/
    @Autowired
    private CustomerRepository repository;

    /*aftereach annotation here will call this method every time after the each @Test method called*/
    @AfterEach
    void deleteAll(){
        repository.deleteAll();
    }

    @Test
    void findByName() {

        Customer customer = new Customer("walle",22L,"male","123");
        Customer customerOne = new Customer("eva",22L,"female","1234");
        repository.save(customer);
        repository.save(customerOne);

        Customer returnedCustomer = repository.findByName("walle");

        assertThat(returnedCustomer).isEqualTo(customer);

    }


    @Test
    void ifFindByNameNotExist() {

        Customer returnedCustomer = repository.findByName("eva");

        assertThat(returnedCustomer).isNull();

    }

}