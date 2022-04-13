package com.example.homework.customer.repo;

import com.example.homework.customer.data.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
    
    public Customer findByName(String name);

    @Modifying
    @Transactional
    @Query("update Customer c set c.reward = :reward where c.id = :id")
    public void updateCustomerSetRewardForId(@Param("reward") Integer reward,@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Customer c set c.pdfbinary = :binary where c.id = :id")
    public void updateCustomerSetPdfBinaryForId(@Param("id") Long id,@Param("binary") String binary);
}
