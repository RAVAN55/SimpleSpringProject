package com.example.homework.helpers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import com.example.homework.model.Customer;
import com.example.homework.model.Product;
import com.example.homework.model.Purchase;
import com.example.homework.model.Range;
import com.example.homework.repository.CustomerRepository;
import com.example.homework.repository.ProductRepository;
import com.example.homework.repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;




public class Helper {

    private List<String> months = Arrays.asList(
        "JANUARY","FEBRUARY","MARCH",
        "APRIL","MAY","JUNE","JULY",
        "AUGUST", "SEPTEMBER", "OCTOBER",
        "NOVEMBER", "DECEMBER"
    );
    

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;


    public Product isProductExist(String name) throws ProductNotFoundException {
        Product data = productRepository.findByName(name);

        if(data == null){
            throw new ProductNotFoundException("product with name " + name + " not found");
        }

        return data;
    }

    public Integer calculateReward(Integer price) {

        Integer fifty;
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

    public Customer isCustomerExist(String name) throws UserNotFoundException {
        Customer data = customerRepository.findByName(name);

        if(data == null){
            throw new UserNotFoundException("no user with name " + name + " found");
        }

        return data;
    }

    public void updateRewardForUser(String name) {
        Integer totalReward = 0;

        Customer customerData = customerRepository.findByName(name);

        List<Purchase> purchaseData = purchaseRepository.findByCustomerId(customerData.getId());

        totalReward = purchaseData.stream().map(Purchase::getReward).reduce(0,(first,next) -> first+next);

        customerRepository.updateCustomerSetRewardForName(totalReward,name);

    }

    /* method return month in integet with zero based index */
    public Integer getMonthInInteger(String month){
        if(months.contains(month.toUpperCase())){
            return months.indexOf(month.toUpperCase());
        }
        return null;
    }

    public Boolean isMonthValid(String month){

        return months.contains(month.toUpperCase()) ? true : false;

    }

    public Integer rewardOfSpecifiedMonthIs(String name, String month, Integer year) {

        Customer customer = customerRepository.findByName(name);

        /* we could check if customer exist but we already did that in controller so dont need to do that again */

        LocalDate start = getMonthStart(month,year);
        LocalDate end = getMonthEnd(month,year);

        List<Purchase> rangeData = purchaseRepository.findByCustomerIdAndCreatedatBetween(customer.getId(), start, end);

        return calculateMonthlyReward(rangeData);
    }

    /* method will return 0 if purchase not found in specified date range */
    private Integer calculateMonthlyReward(List<Purchase> rangeData) {

        Integer total = 0;

        if(rangeData == null){
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
}
