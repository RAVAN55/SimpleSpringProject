package com.example.homework.controller;

import java.util.List;

import com.example.homework.product.data.Product;
import com.example.homework.product.repo.ProductRepository;

import com.example.homework.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts(){
        return service.getProducts();
    }
}
