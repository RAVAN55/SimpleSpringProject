package com.example.homework.controller;

import com.example.homework.pdf.data.Pdf;
import com.example.homework.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/pdf")
    public String pdfHomePage(Model model){
        model.addAttribute("greeting","welcome to pdf creating form");
        return "homepage";
    }

    @PostMapping(value = "/pdf", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createPdf(Pdf pdf, Model model) throws DocumentException, IOException {
        Pdf returnedPdf = pdfService.createPDF(pdf);
        model.addAttribute("user",returnedPdf);
        return "created";
    }

}


