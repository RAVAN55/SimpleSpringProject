package com.example.homework.service;


import com.example.homework.helpers.Helper;
import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.model.CustomerDTO;
import com.example.homework.model.Purchase;
import com.example.homework.repository.CustomerRepository;
import com.example.homework.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {


    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private Helper helper;

    /*this constructor is used in Testing*/
    public CustomerService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public List<Customer> getCustomer(){
        return  customerRepository.findAll();
    }


    public Customer getCustomerByName(String name) throws UserNotFoundException {
        Customer customer = customerRepository.findByName(name);

        if(customer == null){
            throw new UserNotFoundException(String.format("No user found with name %s", name));
        }

        return  customer;
    }


    public Integer getMonthRewardByCustomerName(String name,String month, Integer year) throws UserNotFoundException,InvalidDateException {

        Customer customer = customerRepository.findByName(name);

        if(customer == null){
            throw new UserNotFoundException(String.format("Customer with name %s is not found", name));
        }

        boolean isMonthValid = helper.isMonthValid(month);

        if(!isMonthValid){
            throw new InvalidDateException(String.format("%s is invalid",month));
        }

        if(year < 0){
            throw new InvalidDateException("year can not be negative");
        }

        List<Purchase> customerPurchaseHistory = purchaseRepository.findByCustomerId(customer.getId());

        return customerPurchaseHistory.stream().filter(purchase -> purchase.getCreatedat().getMonth().name().equals(month.toUpperCase()))
                .map(Purchase::getReward)
                .reduce(0, Integer::sum);
    }

    public void createCustomer(CustomerDTO customer) throws UserAlreadyExistException {

        Customer existCustomer = customerRepository.findByName(customer.getDTOName());

        if(existCustomer != null){
            throw new UserAlreadyExistException(String.format("User with name %s is already exist",customer.getDTOName()));
        }

        existCustomer = new Customer(customer.getDTOName(), customer.getDTOAge(), customer.getDTOGender(), customer.getDTOPhone());

        customerRepository.save(existCustomer);

    }
}
