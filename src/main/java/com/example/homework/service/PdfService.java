package com.example.homework.service;

import com.example.homework.customer.data.Customer;
import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.pdf.data.Pdf;
import com.example.homework.product.repo.PdfRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private PdfRepository repository;

    @Autowired
    private CustomerService cService;

    /*we can save this line in application.properties if we want*/
    private static final String pathToStatic = "./src/main/resources/static/";

    Logger log = LoggerFactory.getLogger(PdfService.class);

    public PdfService(PdfRepository repository) {
        this.repository = repository;
    }

    public Pdf createPDF(Pdf pdf) throws UserAlreadyExistException, FileNotFoundException, DocumentException {

        Document document = new Document();
        Font font;
        Chunk chunk;
        Barcode128 barcode128 = new Barcode128();
        Image image;
        File file;

        List<Pdf> isUserExist = repository.findAll();

        for (Pdf usrPdf : isUserExist){
            if (usrPdf.getFirstName().equals(pdf.getFirstName())){
                throw new UserAlreadyExistException(String.format("user with name %s is already exist",pdf.getFirstName()));
            }
        }

        file = new File(pathToStatic + pdf.getFirstName() + ".pdf");

        PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(file));
        document.open();

        font = FontFactory.getFont(FontFactory.HELVETICA,16,BaseColor.BLACK);
        chunk = new Chunk("Hi, " + pdf.getFirstName(),font);

        /*adding file info here*/
        document.addAuthor("Aditya K");
        document.addCreator("AdiKal");
        document.addCreationDate();

        /*adding content in PDF here*/
        document.add(new Paragraph(String.format("pdf of user %s, it contain info of this person and also barcode of their info",pdf.getFirstName())));
        document.add(new Paragraph("\n"));
        document.add(chunk);
        document.add(new Paragraph(String.format("Last Name: %s",pdf.getLastName()),font));
        document.add(new Paragraph(String.format("Gender: %s",pdf.getGender()),font));
        document.add(new Paragraph(String.format("age: %s",pdf.getAge()),font));

        document.add(new Paragraph("-------------------------------------------------------------------"));

        /*for firstname*/
        barcode128.setCode(pdf.getFirstName());
        barcode128.setCodeType(Barcode.CODE128);
        image = barcode128.createImageWithBarcode(pdfWriter.getDirectContent(),BaseColor.BLACK,BaseColor.BLACK);
        image.setAbsolutePosition(40,570);
        image.scalePercent(180,130);
        document.add(image);

        /*for lastname*/
        barcode128.setCode(pdf.getLastName());
        barcode128.setCodeType(Barcode.CODE128);
        image = barcode128.createImageWithBarcode(pdfWriter.getDirectContent(),BaseColor.BLACK,BaseColor.BLACK);
        image.setAbsolutePosition(40,500);
        image.scalePercent(180,130);
        document.add(image);

        /*for gender*/
        barcode128.setCode(pdf.getGender());
        barcode128.setCodeType(Barcode.CODE128);
        image = barcode128.createImageWithBarcode(pdfWriter.getDirectContent(),BaseColor.BLACK,BaseColor.BLACK);
        image.setAbsolutePosition(40,440);
        image.scalePercent(180,130);
        document.add(image);

        /*for age*/
        barcode128.setCode(pdf.getAge().toString());
        barcode128.setCodeType(Barcode.CODE128);
        image = barcode128.createImageWithBarcode(pdfWriter.getDirectContent(),BaseColor.BLACK,BaseColor.BLACK);
        image.setAbsolutePosition(40,380);
        image.scalePercent(180,130);
        document.add(image);

        repository.save(pdf);

        document.close();
        return pdf;
    }

}
