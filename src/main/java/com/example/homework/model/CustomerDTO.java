package com.example.homework.model;

public class CustomerDTO {

    private String name;
    private Long age;
    private String gender;
    private String phone;

    public CustomerDTO(String name,Long age,String gender, String phone){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public String getDTOName() {
        return name;
    }

    public void setDTOName(String name) {
        this.name = name;
    }

    public Long getDTOAge() {
        return age;
    }

    public void setDTOAge(Long age) {
        this.age = age;
    }

    public String getDTOGender() {
        return gender;
    }

    public void setDTOGender(String gender) {
        this.gender = gender;
    }

    public String getDTOPhone() {
        return phone;
    }

    public void setDTOPhone(String phone) {
        this.phone = phone;
    }
}
