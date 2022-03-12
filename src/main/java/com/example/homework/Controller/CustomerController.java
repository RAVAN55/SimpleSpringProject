package com.example.homework.Controller;

import java.util.List;

import com.example.homework.helpers.Helper;
import com.example.homework.helpers.InvalidMonthException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @Autowired
    private Helper helper;
    

    /* all the user in databse */
    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }


    /* get the single user */
    @GetMapping("/{user}")
    public Customer singleCustomer(@PathVariable("user") String name) throws UserNotFoundException {
        Customer customer = new Customer();
        try{
            customer = helper.isCustomerExist(name);
        }catch(Exception e){
            if(customer.getName() == null || !customer.getName().equals(name)){
                throw new UserNotFoundException("no user found with name: " + name);
            }
        }

        return customer;
    }


    /* to get total rewards by user */
    @GetMapping("/{user}/rewards")
    public Integer getTotalReward(@PathVariable("user") String user) throws Exception{
        Customer customer = new Customer();
        try{
            customer = helper.isCustomerExist(user);
        }catch(Exception e){
            if(customer.getName() == null || !customer.getName().equals(user)){
                throw new UserNotFoundException(String.format("user with name %s not found", user));
            }
        }
        return customer.getReward();
    }


    /* to get total rewards in specific month for specific user */
    @GetMapping("/{user}/{month}/{year}/rewards")
    public Integer getMonthReward(@PathVariable("user") String name, @PathVariable("month") String month, @PathVariable("year") Integer year) throws Exception {
        Customer customer = new Customer();
        Boolean validmonth = false;

        try{
            customer = helper.isCustomerExist(name);
        }catch(Exception e){
            if(customer.getName() == null || !customer.getName().equals(name)){
                throw new UserNotFoundException(String.format("user with name %s not found: ", name));
            }
        }

        validmonth = helper.isMonthValid(month);

        if(!validmonth){
            throw new InvalidMonthException(String.format("%s is not a valid month", month));
        }

        if(year < 0){
            throw new Exception("Negative years not allowed\n");
        }

        return helper.rewardOfSpecifiedMonthIs(name,month,year);
    }


    /* save customer into database */
    @PostMapping
    public void saveCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
    }
}
