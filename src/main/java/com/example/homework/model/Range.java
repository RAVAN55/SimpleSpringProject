package com.example.homework.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Range {
    
    private LocalDate startDate;
    private LocalDate endDate;
    
    public Range() {
    }
    
    public Range(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Range [endDate=" + endDate + ", startDate=" + startDate + "]";
    }

}
