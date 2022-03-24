package com.example.homework.service;


import com.example.homework.model.Product;
import com.example.homework.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts(){
        return repository.findAll();
    }
}
