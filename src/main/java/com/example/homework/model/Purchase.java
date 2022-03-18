package com.example.homework.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Purchase{


    @Id
    @SequenceGenerator(name = "purchaseIdGenerator", sequenceName = "purchaseIdGenerator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseIdGenerator")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
    private String productname;
    private Integer price;
    private Integer reward;
    private LocalDate createdat;

    public Purchase(){}


    public Purchase(String productname, Integer price) {
        this.productname = productname;
        this.price = price;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getProductName() {
        return productname;
    }


    public void setProductName(String productname) {
        this.productname = productname;
    }


    public Integer getPrice() {
        return price;
    }


    public void setPrice(Integer price) {
        this.price = price;
    }


    public Integer getReward() {
        return reward;
    }


    public void setReward(Integer reward) {
        this.reward = reward;
    }


    public LocalDate getCreatedat() {
        return createdat;
    }


    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }


    @Override
    public String toString() {
        return "Purchase [createdat=" + createdat + ", id=" + id + ", price=" + price + ", productname=" +productname
                + ", reward=" + reward + "]";
    }

}