package com.example.homework.controller;

import com.example.homework.helpers.UserAlreadyExistException;
import com.example.homework.pdf.data.Pdf;
import com.example.homework.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;


@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/pdf")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String pdfHomePage(Model model){
        model.addAttribute("greeting","welcome to the thymeleaf page");
        return "homepagePdf";
    }

    @PostMapping("/pdf")
    public String createPdf(@RequestBody Pdf pdf, Model model) throws UserAlreadyExistException, DocumentException, FileNotFoundException {
        Pdf returnedPdf = pdfService.createPDF(pdf);
        model.addAttribute("user",returnedPdf);
        return "pdfGenerated";
    }

}


