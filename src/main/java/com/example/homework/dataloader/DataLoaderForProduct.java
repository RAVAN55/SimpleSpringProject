package com.example.homework.dataloader;

import java.util.Arrays;
import java.util.List;

import com.example.homework.product.data.Product;
import com.example.homework.product.repo.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


//@Component
public class DataLoaderForProduct implements CommandLineRunner{

    @Autowired
    private ProductRepository repo;

    @Autowired
    public DataLoaderForProduct(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product> data = Arrays.asList(
            new Product("computer", "i am computer", 150),
            new Product("phone", "i am phone", 200),
            new Product("watch", "i am watch", 50),
            new Product("ring", "i am ring", 80),
            new Product("cycle", "i am cycle", 120),
            new Product("house","i am house", 5000),
            new Product("car", "i am car", 2500),
            new Product("yacht", "i am yacht", 10000),
            new Product("bike", "i am bike", 1200)
        );

        repo.saveAll(data);
    }
    
}
