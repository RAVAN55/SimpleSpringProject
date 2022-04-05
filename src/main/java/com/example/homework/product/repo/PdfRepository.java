package com.example.homework.product.repo;

import com.example.homework.pdf.data.Pdf;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends MongoRepository<Pdf,String> {

    public Pdf findByFirstName(String name);
}
