package com.example.homework.product.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Product {
    

    @Id
    @SequenceGenerator(name = "productIdGenerator", sequenceName = "productIdGenerator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdGenerator")
    private Long id;
    private String name;
    private String description;
    private Integer price;

    
    public Product() {
    }


    public Product(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public String getProductName() {
        return name;
    }


    public void setProductName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getPrice() {
        return price;
    }


    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product [description=" + description + ", id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}
