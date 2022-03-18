package com.example.homework.repository;

import com.example.homework.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    
    public Customer findByName(String name);

    @Modifying
    @Transactional
    @Query("update Customer c set c.reward = :reward where c.name = :name")
    public void updateCustomerSetRewardForName(@Param("reward") Integer reward,@Param("name") String name);
}
