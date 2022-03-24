package com.example.homework.controller;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.ProductNotFoundException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.Product;
import com.example.homework.model.Purchase;
import com.example.homework.model.Range;
import com.example.homework.repository.PurchaseRepository;
import com.example.homework.service.CustomerService;
import com.example.homework.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PurchaseControllerTest {


    @Autowired
    private PurchaseController controller;

    @Mock
    private CustomerService customerService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private Helper helper;

    @BeforeEach
    void set(){
        controller = new PurchaseController(purchaseRepository,purchaseService,customerService,helper);
    }

    @Test
    void getPurchase() {
        Mockito.when(purchaseService.getPurchases()).thenReturn(getListOfPurchase());

        List<Purchase> result = controller.getPurchase();

        Mockito.verify(purchaseService,Mockito.times(1)).getPurchases();

        Mockito.verifyNoMoreInteractions(purchaseService);

    }

    @Test
    void getPurchaseByCustomerName() throws UserNotFoundException {

        Mockito.when(customerService.getCustomerByName("aditya")).thenReturn(getCustomer());

        Mockito.when(purchaseService.getPurchaseByCustomerId(anyLong())).thenReturn(getListOfPurchase());

        try {
            controller.getPurchaseByCustomerName("aditya");
        }catch (UserNotFoundException e){
            throw new UserNotFoundException(e.getMessage());
        }

        ArgumentCaptor<String> argumentOfCustomerService = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<Long> argumentOfPurchaseService = ArgumentCaptor.forClass(Long.class);

        /*verifying customer service arguments*/
        Mockito.verify(customerService,Mockito.times(1)).getCustomerByName(argumentOfCustomerService.capture());
        String capturedCustomerArgument = argumentOfCustomerService.getValue();
        assertThat(capturedCustomerArgument).isEqualTo("aditya");

    }


/*
    @Test
    void willThrowIfGetPurchaseByCustomerNameNotFound() throws UserNotFoundException {


        try {
            controller.getPurchaseByCustomerName("aditya");
        }catch (UserNotFoundException e){
            throw new UserNotFoundException(String.format("no user found with name %s","aditya"));
        }

        Mockito.verify(purchaseService,Mockito.never());
    }
*/


    @Test
    void getPurchaseByRange() throws Exception {

        Mockito.when(customerService.getCustomerByName("aditya")).thenReturn(getCustomer());

        Mockito.doNothing().when(helper).validateRange(getRange());

        Mockito.when(purchaseService.findByCustomerIdAndCreatedatBetween(getCustomer().getId(),getRange().getStartDate(),getRange().getEndDate()))
                .thenReturn(getListOfPurchase());

        try {
            controller.getPurchaseByRange("aditya",getRange());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Mockito.verify(customerService,Mockito.times(1)).getCustomerByName("aditya");

        Mockito.verify(helper,Mockito.times(1)).validateRange(getRange());

        Mockito.verify(purchaseService,Mockito.times(1)).findByCustomerIdAndCreatedatBetween(getCustomer().getId(),getRange().getStartDate(),getRange().getEndDate());

    }



    @Test
    void getRewardsByRange() throws Exception {

        Customer customer = getCustomer();

        Range range = getRange();

        Mockito.when(customerService.getCustomerByName("aditya")).thenReturn(customer);

        Mockito.doNothing().when(helper).validateRange(range);

        Mockito.when(purchaseService.calculateRewardByDateRange(customer,range)).thenReturn(10000);

        try {
            controller.getRewardsByRange("aditya",range);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(customerService,Mockito.times(1)).getCustomerByName(captor.capture());

        String capturedValue = captor.getValue();

        assertThat(capturedValue).isEqualTo("aditya");

        Mockito.verify(purchaseService,Mockito.times(1)).calculateRewardByDateRange(customer,range);

    }

    @Test
    void purchaseProduct() throws Exception {

        Mockito.when(purchaseService.getProductByName("watch")).thenReturn(getProduct());

        Mockito.when(customerService.getCustomerByName("aditya")).thenReturn(getCustomer());

        Mockito.doNothing().when(purchaseService).purchaseItem(getProduct(),getCustomer());

        try {
            controller.purchaseProduct("aditya","watch");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(purchaseService,Mockito.times(1)).getProductByName(captor.capture());

        String capturedValue = captor.getValue();

        assertThat(capturedValue).isEqualTo("watch");

    }


    List<Purchase> getListOfPurchase(){

        Purchase purchase = new Purchase("one",1);
        purchase.setReward(10);
        purchase.setCreatedat(LocalDate.now());

        Purchase purchase1 = new Purchase("two",2);
        purchase.setReward(20);
        purchase.setCreatedat(LocalDate.now());

        Purchase purchase2 = new Purchase("three",3);
        purchase.setReward(30);
        purchase.setCreatedat(LocalDate.now());

        return Arrays.asList(purchase,purchase1,purchase2);
    }

    Customer getCustomer(){
        return new Customer("aditya",22L,"male","123");
    }

/*
    Range getRange(){
        return new Range(LocalDate.now(),LocalDate.now().withDayOfMonth(20).withMonth(3).withYear(2022));
    }
*/

    Range getRange(){
        return  new Range(LocalDate.now().withDayOfMonth(20).withMonth(3).withYear(2022),LocalDate.now());
    }

    Product getProduct(){
        return new Product("watch","i am watch",100);
    }
}