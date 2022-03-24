package com.example.homework.repository;

import com.example.homework.model.Customer;
import com.example.homework.model.Product;
import com.example.homework.model.Purchase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository repository;

    @AfterEach
    void delete(){
        repository.deleteAll();
    }

    @Test
    void findByProductname() {
        Purchase purchase = new Purchase("watch",2999);

        purchase.setReward(100);

        purchase.setCreatedat(LocalDate.now());

        repository.save(purchase);

        Purchase result = repository.findByProductname("watch");

        assertThat(result).isEqualTo(purchase);

    }

    @Test
    void findByCustomerId() {

        Purchase purchase = new Purchase("watch",2000);
//        Purchase purchase1 = new Purchase("land",10000);

        purchase.setReward(100);
//        purchase1.setReward(100000);

        purchase.setCreatedat(LocalDate.now());
//        purchase1.setCreatedat(LocalDate.now());

        purchase.setCustomer(getCustomer());
//        purchase1.setCustomer(getCustomer1());

        repository.save(purchase);
//        repository.save(purchase1);

        List<Purchase> result = repository.findByCustomerId(purchase.getCustomer().getId());

        List<Purchase> compare = repository.findByCustomerId(purchase.getCustomer().getId());

        assertThat(result).isEqualTo(compare);

    }

    @Test
    void findByCustomerIdAndCreatedatBetween() {

        Customer customer = getCustomer();

        Purchase purchase = new Purchase("watch",2000);
        Purchase purchase1 = new Purchase("bike",100440);

        purchase.setReward(100);
        purchase1.setReward(5832958);

        purchase.setCreatedat(LocalDate.now());
        purchase1.setCreatedat(LocalDate.now().withDayOfMonth(20).withMonth(3).withYear(2022));

        purchase.setCustomer(customer);
        purchase1.setCustomer(customer);

        repository.save(purchase);
        repository.save(purchase1);

        List<Purchase> result = repository.findByCustomerIdAndCreatedatBetween(customer.getId(),LocalDate.now(),LocalDate.now().withDayOfMonth(20).withMonth(3).withYear(2022));
        List<Purchase> result1 = repository.findByCustomerIdAndCreatedatBetween(customer.getId(),LocalDate.now(),LocalDate.now().withDayOfMonth(20).withMonth(3).withYear(2022));

        assertThat(result).isEqualTo(result1);


    }

    Customer getCustomer(){
        return new Customer("aditya",22L,"male","123");
    }

    Customer getCustomer1(){
        return new Customer("ram",12L,"male","345");
    }
}