package com.example.homework.product.repo;


import com.example.homework.product.data.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    public Product findByName(String name);
}
