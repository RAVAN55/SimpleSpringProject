package com.example.homework.purchase.data;

import java.time.LocalDate;

import javax.persistence.*;


@Entity
public class Purchase{


    @Id
    @SequenceGenerator(name = "purchaseIdGenerator", sequenceName = "purchaseIdGenerator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseIdGenerator")
    private Long id;

    private Long customerId;
    private String productname;
    private Integer price;
    private Integer reward;
    private LocalDate createdat;

    public Purchase(){}


    public Purchase(String productname, Integer price) {
        this.productname = productname;
        this.price = price;
    }


    public Long getCustomer() {
        return customerId;
    }

    public void setCustomer(Long id){
        this.customerId = id;
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