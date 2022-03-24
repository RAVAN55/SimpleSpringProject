package com.example.homework.service;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.ProductNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.Product;
import com.example.homework.model.Purchase;
import com.example.homework.model.Range;
import com.example.homework.repository.ProductRepository;
import com.example.homework.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {


    @Autowired
    private final PurchaseRepository purchaseRepository;

    @Autowired
    private Helper helper;

    @Autowired
    private final ProductRepository productRepository;

    public PurchaseService(PurchaseRepository purchaseRepository,ProductRepository productRepository, Helper helper) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.helper = helper;
    }

    public List<Purchase> getPurchases() {
       return purchaseRepository.findAll();
    }

    public Product getProductByName(String name) throws ProductNotFoundException{

        Product product = productRepository.findByName(name);
        if(product == null){
            throw new ProductNotFoundException(String.format("product with name %s not found",name));
        }

        return product;
    }

    public List<Purchase> getPurchaseByCustomerId(Long id) {
        return  purchaseRepository.findByCustomerId(id);
    }

    public List<Purchase> findByCustomerIdAndCreatedatBetween(Long id, LocalDate startDate, LocalDate endDate) {
        return purchaseRepository.findByCustomerIdAndCreatedatBetween(id,startDate,endDate);
    }

    public Integer calculateRewardByDateRange(Customer customer, Range range) {
        /*we have to calculate reward in specified range use stream APIs*/

        List<Purchase> purchasesInRange = purchaseRepository.findByCustomerIdAndCreatedatBetween(customer.getId(),range.getStartDate(),range.getEndDate());

        return  purchasesInRange.stream().map(Purchase::getReward).reduce(0,(first,next) -> first+next);

    }

    public void purchaseItem(Product product, Customer name) {
        
        Integer rewardForThisPurchase = helper.calculateReward(product.getPrice());

        Purchase purchase = new Purchase(product.getProductName(), product.getPrice());
        purchase.setReward(rewardForThisPurchase);
        purchase.setCreatedat(LocalDate.now());
        purchase.setCustomer(name);
        purchaseRepository.save(purchase);
        helper.updateRewardForUser(name.getName());

    }
}
