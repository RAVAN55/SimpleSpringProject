package com.example.homework.product.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;


@Document(collection = "product")
public class Product {
    
    @Id
    private String id;
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
