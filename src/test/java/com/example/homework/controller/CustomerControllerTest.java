package com.example.homework.controller;

import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureDataJpa
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void set(){
        customerController = new CustomerController(customerService);
    }


    @Test
    void getCustomerTest() throws Exception {

        Mockito.when(customerService.getCustomer()).thenReturn(getCustomerList());

        customerController.getCustomer();

        Mockito.verify(customerService, Mockito.times(1)).getCustomer();

    }



    @Test
    void getCustomerByName() throws Exception {

        Mockito.when(customerService.getCustomerByName("dodo")).thenReturn(new Customer("dodo",22L,"male","123"));

        Customer customer;

        try {
            customer = customerController.getCustomerByName("dodo");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerService,Mockito.times(1)).getCustomerByName(captor.capture());
        String capturedValue = captor.getValue();
        assertThat(capturedValue).isEqualTo(customer.getName());

        Mockito.verify(customerService,Mockito.times(1)).getCustomerByName("dodo");
        Mockito.verifyNoMoreInteractions(customerService);
    }


    @Test
    void getCustomerTotalRewardByName() throws UserNotFoundException {

        Mockito.when(customerService.getCustomerByName("aditya")).thenReturn(getCustomer());

        try {
            customerController.getCustomerTotalRewardByName("aditya");
        }catch (UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerService,Mockito.times(1)).getCustomerByName(captor.capture());
        String capturedValue = captor.getValue();
        assertThat(capturedValue).isEqualTo("aditya");
        Mockito.verifyNoMoreInteractions(customerService);
    }



    @Test
    void getMonthRewardByCustomerName() throws UserNotFoundException, InvalidDateException {

        String name = "aditya";
        String month = "may";
        Integer year = 2022;

        Mockito.when(customerService.getMonthRewardByCustomerName(name,month,year)).thenReturn(100);

        Integer reward = customerController.getMonthRewardByCustomerName(name,month,year);

        assertThat(reward).isEqualTo(100);

        Mockito.verify(customerService,Mockito.times(1)).getMonthRewardByCustomerName(name,month,year);

        Mockito.verifyNoMoreInteractions(customerService);
    }

    @Test
    void saveCustomer() throws UserAlreadyExistException {

        Mockito.doNothing().when(customerService).createCustomer(any());

        customerController.saveCustomer(any());

        Mockito.verify(customerService,Mockito.times(1)).createCustomer(any());
        Mockito.verifyNoMoreInteractions(customerService);
    }


    List<Customer> getCustomerList(){
       return Arrays.asList(new Customer("aditya",22L,"male","123"),new Customer("roy",22L,"male","234"));
    }


    Customer getCustomer(){
        return new Customer("aditya",22L,"male","123");
    }
}