package com.example.homework.purchase.repo;

import java.time.LocalDate;
import java.util.List;

import com.example.homework.purchase.data.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long>{
    
    public Purchase findByProductname(String productname);

    public List<Purchase> findByCustomerId(Long id);

    /* return purchase between specific time range */
    public List<Purchase> findByCustomerIdAndCreatedatBetween(Long id , LocalDate start, LocalDate end);
}
