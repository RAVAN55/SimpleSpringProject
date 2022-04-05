package com.example.homework.pdf.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "pdf")
public class Pdf {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;

    public Pdf(String firstName, String lastName, Integer age, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

}
