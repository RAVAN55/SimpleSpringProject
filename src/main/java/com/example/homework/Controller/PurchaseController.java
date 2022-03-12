package com.example.homework.Controller;

import java.time.LocalDate;
import java.util.List;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.ProductNotFoundException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.Product;
import com.example.homework.model.Purchase;
import com.example.homework.model.Range;
import com.example.homework.repository.PurchaseRepository;

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
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private Helper helper;

    /* get all the purchases */
    @GetMapping
    public List<Purchase> getPurchase(){
        return purchaseRepository.findAll();
    }

    /* get purchase by customer name */
    @GetMapping("/{customername}")
    public List<Purchase> getPurchaseByUser(@PathVariable("customername") String name) throws Exception {

        Customer customer = new Customer();

        try{
            customer = helper.isCustomerExist(name);
        }catch(Exception e){
            if(customer.getName() == null){
                throw new UserNotFoundException("user not found with name: " + name);
            }
        }

        return purchaseRepository.findByCustomerId(customer.getId());

    }


    /* get purchase by date Range for specific customer */
    @PostMapping("/{customername}")
    public List<Purchase> getPurchaseByRange(@PathVariable("customername") String name, @RequestBody Range range) throws Exception{

        Customer customer = new Customer();
        try{
            customer = helper.isCustomerExist(name);
        }catch(Exception e){
            if(customer.getName() == null){
                throw new UserNotFoundException("user not found with the name: " + name);
            }
            
        }

        return purchaseRepository.findByCustomerIdAndCreatedatBetween(customer.getId(), range.getStartDate(), range.getEndDate());
    }


    /* get reward by date Range for specific customer */
    @PostMapping("/{customername}/rewards")
    public Integer getRewardsByRange(@PathVariable("customername") String name, @RequestBody Range range) throws Exception {

        Customer customer = new Customer();

        try{
            customer = helper.isCustomerExist(name);
        }catch(Exception e){
            if(customer.getName() == null || !customer.getName().equals(name)){
                throw new UserNotFoundException(String.format("user not found with name %s", name));
            }
        }

        /* here we only checked if year is valid or not we can also check for month and dayofmonth  */
        if(range.getStartDate().getYear() < 0 || range.getEndDate().getYear() < 0){
            throw new Exception("Negative years not allowed");
        }

        return helper.calculateRewardByRange(name,range);

    }



    /* purchase item */
    @PostMapping("/user/{username}/product/{productname}")
    public void purchaseProduct(@PathVariable("username") String name, @PathVariable("productname") String product) throws Exception {
        Product productexist = new Product();
        Customer customerexist = new Customer();

        try{
            productexist = helper.isProductExist(product);
            customerexist = helper.isCustomerExist(name);
        }catch(Exception e){
            if(productexist.getProductName() == null){
                throw new ProductNotFoundException("no product found with the name: " + product);
            }else if(customerexist.getName() == null){
                throw new UserNotFoundException("no user found with the name: " + name);
            }else{
                throw new Exception("Something is wrong try again later: " + e.getMessage());
            }
        }

        Integer calculatedReward = helper.calculateReward(productexist.getPrice());

        Purchase item = new Purchase(productexist.getProductName(), productexist.getPrice());

        item.setCreatedat(LocalDate.now());

        item.setReward(calculatedReward);

        customerexist.addIntoPurchaseSet(item);

        item.setCustomer(customerexist);
        purchaseRepository.save(item);
        helper.updateRewardForUser(name);

    }
}
