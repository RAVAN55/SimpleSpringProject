package com.example.homework.dataloader;

import java.util.Arrays;
import java.util.List;

import com.example.homework.model.Customer;
import com.example.homework.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoaderForCustomer implements CommandLineRunner{

    private CustomerRepository customerRepository;

    @Autowired
    public DataLoaderForCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        final String female = "female";

        List<Customer> data = Arrays.asList(
            new Customer("aditya", 22L,"male","123456789"),
            new Customer("mohan", 25L,"male","3847936"),
            new Customer("prem", 30L,"male","293898234"),
            new Customer("rohan", 24L,"male","525743"),
            new Customer("soham", 20L,"male","079082398"),
            new Customer("preeti", 22L, female, "293653"),
            new Customer("prajakta", 23L, female, "98739486"),
            new Customer("radha", 25L, female, "1111111111")
        );

        customerRepository.saveAll(data);
    }
    
}
