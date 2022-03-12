package com.example.homework.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.homework.model.Customer;
import com.example.homework.model.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long>{
    
    public Purchase findByProductname(String productname);

    public List<Purchase> findByCustomerId(Long id);

    public void save(List<Customer> saveData);

    /* return purchase between specific time range */
    public List<Purchase> findByCustomerIdAndCreatedatBetween(Long id , LocalDate start, LocalDate end);
}
