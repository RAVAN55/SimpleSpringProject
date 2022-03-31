package com.example.homework.dataloader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.homework.purchase.data.Purchase;
import com.example.homework.purchase.repo.PurchaseRepository;
import com.example.homework.customer.data.Customer;
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
        one.setCustomer(getRajveer().getId());
        one.setReward(50);

        /* two */
        Purchase two = new Purchase("cycle", 120);
        two.setCreatedat(LocalDate.of(2022, 03, 1));
        two.setCustomer(getPramod().getId());
        two.setReward(90);

        /* three */
        Purchase three = new Purchase("computer", 150);
        three.setCreatedat(LocalDate.of(2021, 11, 27));
        three.setCustomer(getSmita().getId());
        three.setReward(150);

        /* four */
        Purchase four = new Purchase("house", 5000);
        four.setCreatedat(LocalDate.of(2021, 04, 10));
        four.setCustomer(getGeeta().getId());
        four.setReward(9850);


        List<Purchase> saveData = Arrays.asList(one,two,three,four);
        purchaseRepository.saveAll(saveData);
    }

    public Customer getRajveer(){
        return new Customer("rajveer",22L,"male","2375");
    }


    public Customer getPramod(){
        return new Customer("pramod",22L,"male","2375");
    }



    public Customer getSmita(){
        return new Customer("smita",22L,"female","2375");
    }



    public Customer getGeeta(){
        return new Customer("geeta",22L,"female","2375");
    }
}
