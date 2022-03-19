package com.example.homework.service;

import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.CustomerDTO;
import com.example.homework.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;


@DataJpaTest
/*RunWith annotation is impo if you are using @Mock annotation */
@RunWith(MockitoJUnitRunner.class)
class CustomerServiceTest {

    private CustomerService service;
    /**/
    @Mock
    private CustomerRepository repository;
    private AutoCloseable autoCloseable;


    @BeforeEach
    void set(){
        service = new CustomerService(repository);
    }


    @Test
    void getCustomer() {

        Mockito.when(repository.findAll()).thenReturn(getCustomerList());
        List<Customer> customer = service.getCustomer();

        /*verifying if findAll method is called or not in CustomerRepository*/
        /* test will fail if i replace findAll() with another method because in service.getCustomer() method*/
        /*we called findAll() */

        Mockito.verify(repository).findAll();

/*
        Mockito.verify(repository).deleteAll();
*/
    }

    private List<Customer> getCustomerList() {
        Customer customer = new Customer("one",1L,"male","1");
        Customer customer2 = new Customer("two",2L,"male","2");
        Customer customer3 = new Customer("three",3L,"male","3");
        Customer customer4 = new Customer("four",4L,"male","4");
        Customer customer5 = new Customer("five",5L,"male","5");

        return Arrays.asList(customer,customer2,customer3,customer4,customer5);
    }

    @Test
    void getCustomerByName() throws UserNotFoundException {
        String name = "two";
        Mockito.when(repository.findByName(name)).thenReturn(getSingleCustomer());

        Customer customer = service.getCustomerByName("two");

        Mockito.verify(repository).findByName(name);

       assertEquals("male",customer.getGender());
    }

    private Customer getSingleCustomer() {
        return new Customer("two",1L,"male","1");
    }

    private CustomerDTO createCustomerObject(){
        return new CustomerDTO("two",1L,"male","1");
    }

    @Test
    void getMonthRewardByCustomerName() {

    }

    @Test
    @Disabled
    void createCustomer() throws Exception {
    }
}