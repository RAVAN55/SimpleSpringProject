package com.example.homework.controller;

import java.util.List;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.CustomerDTO;
import com.example.homework.repository.CustomerRepository;
import com.example.homework.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Helper helper;
    

    /* all the user in database */
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomer(){
        return ResponseEntity.ok(customerService.getCustomer());
    }

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    /* get the single customer */
    @GetMapping("/{user}")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable("user") String name) throws UserNotFoundException {
        Customer customer = new Customer();
        try{
            customer = customerService.getCustomerByName(name);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(customer);
    }


    /* to get total rewards by user */
    @GetMapping("/{user}/rewards")
    public ResponseEntity<Integer> getCustomerTotalRewardByName(@PathVariable("user") String name) throws UserNotFoundException {

        Customer customer = new Customer();
        try{
            customer = customerService.getCustomerByName(name);
        }catch(Exception e){
            throw new UserNotFoundException(e.getMessage());
        }

        return ResponseEntity.ok(customer.getReward());
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
