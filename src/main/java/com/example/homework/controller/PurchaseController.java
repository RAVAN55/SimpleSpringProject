package com.example.homework.controller;

import java.util.List;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.ProductNotFoundException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.product.data.Product;
import com.example.homework.purchase.data.Purchase;
import com.example.homework.model.Range;
import com.example.homework.purchase.repo.PurchaseRepository;
import com.example.homework.customer.data.Customer;
import com.example.homework.service.CustomerService;
import com.example.homework.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

/*     @Autowired
    private PurchaseRepository purchaseRepository; */

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private Helper helper;


    public PurchaseController(PurchaseRepository purchaseRepository, PurchaseService purchaseService, CustomerService customerService,Helper helper) {
/*         this.purchaseRepository = purchaseRepository; */
        this.purchaseService = purchaseService;
        this.customerService = customerService;
        this.helper = helper;
    }

    /* get all the purchases */
    @GetMapping
    public List<Purchase> getPurchase(){
        return purchaseService.getPurchases();
    }

    /* get purchase by customer name */
    @GetMapping("/{name}")
    public List<Purchase> getPurchaseByCustomerName(@PathVariable("name") String name) throws UserNotFoundException {

        List<Purchase> purchase;
        Customer customer;

        try{
            customer = customerService.getCustomerByName(name);

            purchase = purchaseService.getPurchaseByCustomerId(customer.getId());
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return purchase;
    }


    /* get purchase by date Range for specific customer */
    @PostMapping("/{name}")
    public List<Purchase> getPurchaseByRange(@PathVariable("name") String name, @RequestBody Range range) throws UserNotFoundException, InvalidDateException{

        Customer customer;
        try{

            customer = customerService.getCustomerByName(name);

            helper.validateRange(range);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return purchaseService.findByCustomerIdAndCreatedatBetween(customer.getId(), range.getStartDate(), range.getEndDate());
    }


    /* get reward by date Range for specific customer */
    @PostMapping("/{name}/rewards")
    public Integer getRewardsByRange(@PathVariable("name") String name, @RequestBody Range range) throws UserNotFoundException, InvalidDateException {

        Customer customer = new Customer();
        try{
            customer = customerService.getCustomerByName(name);

            helper.validateRange(range);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return purchaseService.calculateRewardByDateRange(customer,range);

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

        purchaseService.purchaseItem(productExist,customerExist.getId());

    }
}
