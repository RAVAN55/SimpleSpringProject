package com.example.homework.controller;

import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private Helper helper;

    /* get all the purchases */
    @GetMapping
    public ResponseEntity<List<Purchase>> getPurchase(){
        return ResponseEntity.ok(purchaseService.getPurchases());
    }

    /* get purchase by customer name */
    @GetMapping("/{name}")
    public ResponseEntity<List<Purchase>> getPurchaseByCustomerName(@PathVariable("name") String name) throws UserNotFoundException {

        List<Purchase> purchase;
        Customer customer;

        try{
            customer = customerService.getCustomerByName(name);
            purchase = purchaseService.getPurchaseByCustomerId(customer.getId());
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return ResponseEntity.ok(purchase);
    }


    /* get purchase by date Range for specific customer */
    @PostMapping("/{name}")
    public ResponseEntity<List<Purchase>> getPurchaseByRange(@PathVariable("name") String name, @RequestBody Range range) throws UserNotFoundException, InvalidDateException{

        Customer customer = new Customer();
        try{
            customer = customerService.getCustomerByName(name);
            helper.validateRange(range);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return ResponseEntity.ok(purchaseService.findByCustomerIdAndCreatedatBetween(customer.getId(), range.getStartDate(), range.getEndDate()));
    }


    /* get reward by date Range for specific customer */
    @PostMapping("/{name}/rewards")
    public ResponseEntity<Integer> getRewardsByRange(@PathVariable("name") String name, @RequestBody Range range) throws UserNotFoundException, InvalidDateException {

        Customer customer = new Customer();
        try{
            customer = customerService.getCustomerByName(name);
            helper.validateRange(range);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return ResponseEntity.ok(purchaseService.calculateRewardByDateRange(customer,range));

    }



    /* purchase item */
    @PostMapping("/user/{username}/product/{name}")
    public void purchaseProduct(@PathVariable("username") String name, @PathVariable("name") String product) throws UserNotFoundException,ProductNotFoundException {
        Product productExist = new Product();
        Customer customerExist = new Customer();

        try{
            productExist = purchaseService.getProductByName(product);
            customerExist = customerService.getCustomerByName(name);
        }catch(Exception e){
            throw new ProductNotFoundException(e.getMessage());
        }

        purchaseService.purchaseItem(productExist,customerExist);

    }
}
