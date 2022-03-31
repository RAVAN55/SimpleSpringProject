package com.example.homework.controller;

import java.util.List;

import com.example.homework.helpers.*;
import com.example.homework.customer.data.Customer;
import com.example.homework.model.CustomerDTO;
import com.example.homework.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class CustomerController {


    @Autowired
    private final CustomerService customerService;

    @Autowired
    private Helper helper;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    /* all the user in database */
    @GetMapping
    public List<Customer> getCustomer(){
        return customerService.getCustomer();
    }

    /* get the single customer */
    @GetMapping("/{user}")
    public Customer getCustomerByName(@PathVariable("user") String name) throws UserNotFoundException {
        Customer customer;
        try{
            customer = customerService.getCustomerByName(name);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return customer;
    }


    /* to get total rewards by user */
    @GetMapping("/{user}/rewards")
    public Integer getCustomerTotalRewardByName(@PathVariable("user") String name) throws UserNotFoundException {

        Customer customer = new Customer();
        try{
            customer = customerService.getCustomerByName(name);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return customer.getReward();
    }


    /* to get total rewards for specific month for specific user */
    @GetMapping("/{user}/{month}/{year}/rewards")
    public Integer getMonthRewardByCustomerName(@PathVariable("user") String name,
                                  @PathVariable("month") String month,
                                  @PathVariable("year") Integer year) throws UserNotFoundException, InvalidDateException {


        return customerService.getMonthRewardByCustomerName(name,month,year);

    }


    /* save customer into database */
    @PostMapping
    public void saveCustomer(@RequestBody CustomerDTO customer) throws UserAlreadyExistException {

        try{
            customerService.createCustomer(customer);
        }catch (Exception e){
            throw new UserAlreadyExistException(e.getMessage());
        }

    }
}
