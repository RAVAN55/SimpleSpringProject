package com.example.homework.controller;

import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.pdf.data.Pdf;
import com.example.homework.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.rmi.UnexpectedException;


@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/pdf")
    public String pdfHomePage(Model model){
        model.addAttribute("greeting","welcome to pdf creating form");
        return "homepage";
    }

    @GetMapping("/pdf/{name}/get")
    public String getPdforPerson(@PathVariable("name") String name, Model model) throws UserNotFoundException, IOException {

        String pathToPdf = pdfService.getPdforUser(name);

        model.addAttribute("pdfPath",pathToPdf);

        return "pdf";

    }

    @PostMapping(value = "/pdf", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createPdf(Pdf pdf, Model model) throws DocumentException, IOException, UserNotFoundException {
        Pdf returnedPdf = pdfService.createPDF(pdf);
        model.addAttribute("user",returnedPdf);
        return "created";
    }

}


