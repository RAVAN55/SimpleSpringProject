package com.example.homework.customer.data;


import javax.persistence.*;

@Entity
public class Customer {
    
    @Id
    @SequenceGenerator(name = "CustomerIdGenerator", sequenceName = "CustomerIdGenerator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CustomerIdGenerator")
    private Long id;
    private String name;
    private Long age;
    private String gender;
    private String phone;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer [age=" + age + ", gender=" + gender + ", id=" + id + ", name=" + name + ", phone=" + phone + ", reward=" + reward + "]";
    }
}
