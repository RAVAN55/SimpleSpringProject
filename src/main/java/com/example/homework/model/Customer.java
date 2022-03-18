package com.example.homework.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Customer {
    
    @Id
    @SequenceGenerator(name = "CustomerIdGenerator", sequenceName = "CustomerIdGenerator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerIdGenerator")
    private Long id;
    private String name;
    private Long age;
    private String gender;
    private String phone;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore 
    private Set<Purchase> purchase = new HashSet<>();

    private Integer reward;
    

    public Customer() {
    }


    public Customer(String name, Long age, String gender, String phone) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }


    public Integer getReward() {
        return reward;
    }


    public void setReward(Integer reward) {
        this.reward = reward;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Long getAge() {
        return age;
    }


    public void setAge(Long age) {
        this.age = age;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Purchase> getPurchases(){
        return purchase;
    }

    public void addIntoPurchaseSet(Purchase purchase){
        this.purchase.add(purchase);
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer [age=" + age + ", gender=" + gender + ", id=" + id + ", name=" + name + ", purchase=" + purchase
                + ", phone=" + phone + ", reward=" + reward + "]";
    }


}
