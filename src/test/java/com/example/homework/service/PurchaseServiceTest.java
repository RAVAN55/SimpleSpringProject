package com.example.homework.service;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.ProductNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.Product;
import com.example.homework.model.Purchase;
import com.example.homework.model.Range;
import com.example.homework.repository.ProductRepository;
import com.example.homework.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;


@DataJpaTest
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Mock
    private Helper helper;

    @BeforeEach
    void set(){
        purchaseService = new PurchaseService(purchaseRepository,productRepository,helper);
    }

    @Test
    void getPurchases() {

        Mockito.when(purchaseRepository.findAll()).thenReturn(getPurchasesList());

        purchaseService.getPurchases();

        Mockito.verify(purchaseRepository).findAll();


    }


    @Test
    void getProductByName() {
        String name = "mouse";

        ProductRepository productSpy = Mockito.spy(productRepository);

        Mockito.doReturn(new Product("mouse","i am mouse",100)).when(productSpy).findByName(name);

        assertThatThrownBy(()-> purchaseService.getProductByName(name)).isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(String.format("product with name %s not found",name));

        Mockito.verify(productRepository,Mockito.times(1)).findByName(name);
    }


    @Test
    void willThrowIfProductIsNull() {
        String name = "mouse";

        ProductRepository productSpy = Mockito.spy(productRepository);

//        Mockito.doReturn(new Product("mouse","i am mouse",100)).when(productSpy).findByName(name);

        assertThatThrownBy(()-> purchaseService.getProductByName(name)).isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(String.format("product with name %s not found",name));

    }



    @Test
    void getPurchaseByCustomerId(){
        Mockito.doReturn(getPurchasesList()).when(purchaseRepository).findByCustomerId(any());

        purchaseService.getPurchaseByCustomerId(any());

        Mockito.verify(purchaseRepository).findByCustomerId(any());

    }


/*
    @Test
    void findByCustomerIdAndCreatedatBetween(){

        Mockito.doReturn(getPurchasesList()).when(purchaseRepository).findByCustomerIdAndCreatedatBetween(any(),eq(LocalDate.now()),eq(LocalDate.now()));

        purchaseService.findByCustomerIdAndCreatedatBetween(any(),eq(LocalDate.now()),eq(LocalDate.now()));

        Mockito.verify(purchaseRepository).findByCustomerIdAndCreatedatBetween(any(),eq(LocalDate.now()),eq(LocalDate.now()));

    }
*/


    @Test
    void purchaseItemTest(){

        Mockito.when(helper.calculateReward(100)).thenReturn(50);

        purchaseService.purchaseItem(getProduct(),getCustomer());

        Mockito.verify(helper, Mockito.times(1)).calculateReward(any());

        Mockito.verify(purchaseRepository,Mockito.times(1)).save((Purchase) any());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(helper,Mockito.times(1)).updateRewardForUser(captor.capture());

        String capturedValue = captor.getValue();

        assertThat(capturedValue).isEqualTo(getCustomer().getName());
    }


    @Test
    void findByCustomerIdAndCreatedatBetween(){

        LocalDate start = LocalDate.now().withDayOfMonth(1).withMonth(1).withYear(2021);
        LocalDate end = LocalDate.now();

        Mockito.when(purchaseRepository.findByCustomerIdAndCreatedatBetween(1L,start,end)).thenReturn(getPurchasesList());

        purchaseService.findByCustomerIdAndCreatedatBetween(1L,start,end);

        Mockito.verify(purchaseRepository,Mockito.times(1)).findByCustomerIdAndCreatedatBetween(1L,start,end);

        Mockito.verifyNoMoreInteractions(purchaseRepository);

    }


    @Test
    void calculateRewardByDateRange(){

        LocalDate start = LocalDate.now().withDayOfMonth(1).withMonth(1).withYear(2022);
        LocalDate end = LocalDate.now();


//        Mockito.when(purchaseRepository.findByCustomerIdAndCreatedatBetween(anyLong(),eq(LocalDate.now().withDayOfMonth(1).withMonth(1).withYear(2022)),LocalDate.now())).thenReturn(getPurchasesList());

        Mockito.when(purchaseRepository.findByCustomerIdAndCreatedatBetween(anyLong(),start,end)).thenReturn(getPurchasesList());

        purchaseService.calculateRewardByDateRange(getCustomer(),getRange());

        Mockito.verify(purchaseRepository,Mockito.times(1)).findByCustomerIdAndCreatedatBetween(anyLong(),start,end);
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

    Product getProduct(){
        return new Product("ring","i am ring",2000);
    }

    Customer getCustomer(){
        return new Customer("aditya",22L,"male","123");
    }

    Customer getCustomerWithId(){
        Customer customer = new Customer("aditya",22L,"male","123");
        customer.setId(1L);
        return customer;
    }


    Range getRange(){
        return new Range(LocalDate.now().withDayOfMonth(1).withMonth(1).withYear(2022),LocalDate.now());
    }
}