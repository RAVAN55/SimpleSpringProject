package com.example.homework.service;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.Purchase;
import com.example.homework.repository.CustomerRepository;
import com.example.homework.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DataJpaTest
/*RunWith annotation is impo if you are using @Mock annotation */
@RunWith(MockitoJUnitRunner.class)
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private Helper helper;

    @BeforeEach
    void set(){
        service = new CustomerService(repository,helper,purchaseRepository);
    }


    @Test
    void getCustomer() {

        Mockito.when(repository.findAll()).thenReturn(getCustomerList());
        service.getCustomer();

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

        Customer customer = service.getCustomerByName(name);

        Mockito.verify(repository).findByName(name);

       assertEquals("male",customer.getGender());
    }



    @Test
    void willThrowIfCustomerIsNullInGetCustomerByName() {
       String name = "aditya";

       assertThatThrownBy(() -> service.getCustomerByName(name))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessageContaining(String.format("No user found with name %s",name));
       }

    @Test
    void willThrowIfCustomerIsNull() {
        String name = "aditya";
        String month = "march";
        Integer year = 2022;

//        Mockito.when(repository.findByName(getSingleCustomer().getName())).thenReturn(getSingleCustomer());

        assertThatThrownBy(() -> service.getMonthRewardByCustomerName(name,month,year))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(String.format("Customer with name %s is not found",name));
        verify(purchaseRepository, never()).findByCustomerId(any());

        verify(purchaseRepository, never()).findByCustomerId(any());
    }



    @Test
    void willThrowIfMonthIsNotValid(){
        String name = "aditya";
        String month = "maya";
        Integer year = 2022;

        when(repository.findByName(name)).thenReturn(new Customer(name,33L,"male","123"));

        Mockito.when(helper.isMonthValid(month)).thenReturn(false);

        assertThatThrownBy(() -> service.getMonthRewardByCustomerName(name,month,year))
                .isInstanceOf(InvalidDateException.class)
                .hasMessageContaining(String.format("month %s is invalid",month));

        verify(repository).findByName(name);

        Mockito.verify(helper,times(1)).isMonthValid(month);

        verify(purchaseRepository, never()).findByCustomerId(any());

    }


    @Test
//    @Disabled
    void getMonthRewardByCustomerName() throws Exception {

        String month = "april";
        Integer year = 2022;

        Mockito.when(repository.findByName("aditya")).thenReturn(getSingleCustomer());

        Mockito.when(helper.isMonthValid(month)).thenReturn(true);

        Mockito.when(purchaseRepository.findByCustomerId(anyLong())).thenReturn(getPurchasesList());

        try {
            service.getMonthRewardByCustomerName("aditya",month,year);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(repository,times(1)).findByName(captor.capture());

        String capturedValue = captor.getValue();

        assertThat(capturedValue).isEqualTo("aditya");

        Mockito.verify(helper,times(1)).isMonthValid(captor.capture());

        String capturedMonthValue = captor.getValue();

        assertThat(capturedMonthValue).isEqualTo("april");

    }



    @Test
    void willThrowIfCustomerIsNotNull() {

        Customer customer = new Customer(createCustomerObject().getName(), createCustomerObject().getAge(), createCustomerObject().getGender(), createCustomerObject().getPhone());
        Mockito.when(repository.findByName(createCustomerObject().getName())).thenReturn(customer);

        assertThatThrownBy(() -> service.createCustomer(createCustomerObject()))
                .isInstanceOf(UserAlreadyExistException.class)
                .hasMessageContaining(String.format("User with name %s is already exist",customer.getName()));
    }



    @Test
    void createCustomer() throws Exception {

        Customer customer = new Customer(createCustomerObject().getName(), createCustomerObject().getAge(), createCustomerObject().getGender(), createCustomerObject().getPhone());
        service.createCustomer(customer);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(repository).save(customerArgumentCaptor.capture());
        Customer customer1 = customerArgumentCaptor.getValue();
        assertThat(customer1).isEqualTo(customer);
        verify(repository).findByName(customer.getName());
    }


    private Customer getSingleCustomer() {
        return new Customer("aditya",1L,"male","1");
    }

    private Customer createCustomerObject(){
        return new Customer("two",1L,"male","1");
    }


    List<Purchase> getPurchasesList(){
        return Arrays.asList(
                new Purchase("mouse",100),
                new Purchase("keyboard",200),
                new Purchase("monitor",400),
                new Purchase("ctable",1000),
                new Purchase("printer",1200)
        );
    }

}