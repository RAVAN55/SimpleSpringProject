package com.example.homework.dataloader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.homework.model.Customer;
import com.example.homework.model.Purchase;
import com.example.homework.repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoaderForPurchase implements CommandLineRunner{

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public void run(String... args) throws Exception {

        /* one */
        Purchase one = new Purchase("ring", 80);
        one.setCreatedat(LocalDate.of(2022, 02, 14));
        one.setCustomer(new Customer("rajveer", 22L, "male", "5952235"));
        one.setReward(50);

        /* two */
        Purchase two = new Purchase("cycle", 120);
        two.setCreatedat(LocalDate.of(2022, 03, 1));
        two.setCustomer(new Customer("pramod", 24L, "male", "2722352"));
        two.setReward(90);

        /* three */
        Purchase three = new Purchase("computer", 150);
        three.setCreatedat(LocalDate.of(2021, 11, 27));
        three.setCustomer(new Customer("smita", 21L, "female", "2359123"));
        three.setReward(150);

        /* four */
        Purchase four = new Purchase("house", 5000);
        four.setCreatedat(LocalDate.of(2021, 04, 10));
        four.setCustomer(new Customer("geeta", 28L, "female", "09809124"));
        four.setReward(9850);


        List<Purchase> saveData = Arrays.asList(one,two,three,four);
        purchaseRepository.saveAll(saveData);
    }
    
}
