package com.example.homework.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.example.homework.customer.data.Customer;
import com.example.homework.pdf.data.Pdf;
import com.example.homework.purchase.data.Purchase;
import com.example.homework.model.Range;
import com.example.homework.customer.repo.CustomerRepository;
import com.example.homework.product.repo.ProductRepository;
import com.example.homework.purchase.repo.PurchaseRepository;

import com.opencsv.CSVWriter;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;


public class Helper {

    private List<String> months = Arrays.asList(
        "JANUARY","FEBRUARY","MARCH",
        "APRIL","MAY","JUNE","JULY",
        "AUGUST", "SEPTEMBER", "OCTOBER",
        "NOVEMBER", "DECEMBER"
    );

    private static final String PATH_TO_STATIC = "./src/main/resources/static/";

    private File filecsv = new File(PATH_TO_STATIC + "userData.csv");

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;


/*
    public Product isProductExist(String name) throws ProductNotFoundException {
        Product data = productRepository.findByName(name);

        if(data == null){
            throw new ProductNotFoundException("product with name " + name + " not found");
        }

        return data;
    }
*/

    public Integer calculateReward(Integer price) {

        Integer fifty = null;
        Integer total = 0;

        if(price > 50){
            fifty = 50;
            if(price > 100){
                Integer temp = price - 100;
                total = temp * 2;
            }
        }else{
            fifty = 0;
        }

        total += fifty;
        return total;
    }


    public void updateRewardForUser(Long id) {
        Integer totalReward = 0;

        Customer customer = new Customer();

        Optional<Customer> customerData = customerRepository.findById(id);

        if(customerData.isPresent()){
            customer = customerData.get();
        }

        List<Purchase> purchaseData = purchaseRepository.findByCustomerId(customer.getId());

        totalReward = purchaseData.stream().map(Purchase::getReward).reduce(0,(first,next) -> first+next);

        customerRepository.updateCustomerSetRewardForId(totalReward,id);

    }

    /* method return month in integet with zero based index */
    public Integer getMonthInInteger(String month){
        if(months.contains(month.toUpperCase())){
            return months.indexOf(month.toUpperCase());
        }
        return null;
    }

    public Boolean isMonthValid(String month){

        return months.contains(month.toUpperCase());

    }

/*
    public Integer rewardOfSpecifiedMonthIs(String name, String month, Integer year) {

        Customer customer = customerRepository.findByName(name);

        */
/* we could check if customer exist but we already did that in controller so dont need to do that again *//*


        LocalDate start = getMonthStart(month,year);
        LocalDate end = getMonthEnd(month,year);

        List<Purchase> rangeData = purchaseRepository.findByCustomerIdAndCreatedatBetween(customer.getId(), start, end);

        return calculateMonthlyReward(rangeData);
    }
*/

    /* method will return 0 if purchase not found in specified date range */
    private Integer calculateMonthlyReward(List<Purchase> rangeData) {

        Integer total = 0;

        if(rangeData.isEmpty()){
            return 0;
        }

        for(Purchase item : rangeData){
            total += item.getReward();
        }

        return total;

    }

    /* return last date of month */
    private LocalDate getMonthEnd(String month, Integer year) {
        
        /* in YearMonth class months  start from 1 not 0 */
        YearMonth ym = YearMonth.of(year, getMonthInInteger(month)+1);

        return LocalDate.now().withDayOfMonth(ym.lengthOfMonth()).withMonth(getMonthInInteger(month)+1).withYear(year);


    }

    /* every month start from date 1 so it returns month with 1st date */
    private LocalDate getMonthStart(String month, Integer year) {

        /* LocalDate withMonth method is not zero based Jan starts from 1 not 0 */

        return LocalDate.now().withDayOfMonth(1).withMonth(getMonthInInteger(month)+1).withYear(year);
    }

    public Integer calculateRewardByRange(String name, Range range) {

        Customer customer =  customerRepository.findByName(name);

        Integer total = 0;

        List<Purchase> rangeData = purchaseRepository.findByCustomerIdAndCreatedatBetween(customer.getId(), range.getStartDate(), range.getEndDate());
        
        for(Purchase item : rangeData){
            total += item.getReward();
        }

        return total;
    }


    public void validateRange(Range range) throws InvalidDateException {
        if (range.getEndDate().isBefore(range.getStartDate())){
            throw new InvalidDateException("End date can not be before of start date");
        }

        if(range.getEndDate().isAfter(LocalDate.now())){
            throw new InvalidDateException("end date can not be after current date");
        }
    }

    public void createCSV(Pdf pdf) throws IOException {

        try{

            FileWriter fwrite = new FileWriter(filecsv,true);

            CSVWriter csvWriter = new CSVWriter(fwrite);

            String userData[] = new String[]{pdf.getFirstName(),pdf.getLastName(),pdf.getGender(),pdf.getAge().toString()};

            csvWriter.writeNext(userData);

            csvWriter.close();

        }catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }

    public void createDOCX(Pdf pdf) throws IOException {

        File filedocx;

        try(XWPFDocument document = new XWPFDocument()){

            filedocx = new File(PATH_TO_STATIC + pdf.getFirstName() + ".docx");

            /*para can be created by method from the document instance*/
            XWPFParagraph para = document.createParagraph();

            /*setting about para*/
            para.setBorderTop(Borders.COMPASS);
            para.setAlignment(ParagraphAlignment.CENTER);

            /*the XWPFRun is font class you can customize font with that*/
            XWPFRun paraContentAndStyling = para.createRun();
            paraContentAndStyling.setCapitalized(true);
            paraContentAndStyling.setFontSize(18);
            paraContentAndStyling.setBold(true);
            paraContentAndStyling.setColor("0E1019");
            paraContentAndStyling.setText("Hello " + pdf.getFirstName() + " this is your doc file");
            paraContentAndStyling.setFontFamily("Fira Code");


            /*para second*/
            XWPFParagraph para2 = document.createParagraph();
            para.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun paraContentAndStyling2 = para2.createRun();
            paraContentAndStyling2.setFontSize(16);
            paraContentAndStyling2.setText("LastName: " + pdf.getLastName());
            paraContentAndStyling2.setFontFamily("Fira Code");


            /*para third*/
            XWPFParagraph para3 = document.createParagraph();
            para.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun paraContentAndStyling3 = para3.createRun();
            paraContentAndStyling3.setFontSize(16);
            paraContentAndStyling3.setText("Gender: " + pdf.getGender());
            paraContentAndStyling3.setFontFamily("Fira Code");



            /*para third*/
            XWPFParagraph para4 = document.createParagraph();
            para.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun paraContentAndStyling4 = para4.createRun();
            paraContentAndStyling4.setFontSize(16);
            paraContentAndStyling4.setText("Age: " + pdf.getAge());
            paraContentAndStyling4.setFontFamily("Fira Code");


            try(FileOutputStream fwri = new FileOutputStream(filedocx)){
                document.write(fwri);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBinaryOfPdfAndSave(String firstName) throws IOException, UserNotFoundException {

        Customer customer = customerRepository.findByName(firstName);

        if(customer == null){
            throw new UserNotFoundException(String.format("user with name %s not found",firstName));
        }

        Path of = Path.of(PATH_TO_STATIC + firstName + ".pdf");
        try{
            byte pdfInByte[] = Files.readAllBytes(of);

            String pdfBinaryInString = Base64.getEncoder().encodeToString(pdfInByte);

            customerRepository.updateCustomerSetPdfBinaryForId(customer.getId(),pdfBinaryInString);

        }catch (IOException e){
            throw new IOException(e.getMessage());
        }

        Files.delete(of);
    }
}
