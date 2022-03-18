package com.example.homework.repository;

import com.example.homework.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
    public Product findByName(String name);
}
