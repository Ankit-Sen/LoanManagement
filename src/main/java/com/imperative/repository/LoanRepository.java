package com.imperative.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class LoanRepository implements ILoanRepository{
    
    private Double averageLoanAmount;
    private Double averageSalary;
    List<String> reasons;

    public LoanRepository(){
        this.averageLoanAmount = 0.0;
        this.averageSalary=0.0; 
        reasons = new ArrayList<>();
    }

    @Override
    public void updateAverageLoanAmount(Double amount){
        if(this.averageLoanAmount > 0){
            this.averageLoanAmount = (averageLoanAmount+amount) / 2;
        }
        this.averageLoanAmount += amount;
    }

    @Override
    public Double getAverageLoanAmount() {
        return this.averageLoanAmount;
    }

    @Override
    public void updateAverageSalary(Double salary){
        if(this.averageSalary > 0){
            this.averageSalary = (averageSalary+salary) / 2;
        }
        this.averageSalary += salary;
    }

    @Override
    public Double getAverageSalary() {
        return this.averageSalary;
    }

    @Override
    public void updateReasons(String reason){
        if(!this.reasons.contains(reason)){
            reasons.add(reason);
        }
    }

    @Override
    public List<String> getReasons() {
        return this.reasons;
    }
}
