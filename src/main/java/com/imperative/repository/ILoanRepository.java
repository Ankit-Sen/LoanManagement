package com.imperative.repository;

import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public interface ILoanRepository {

    public void updateAverageLoanAmount(Double amount);

    public Double getAverageLoanAmount();

    public void updateAverageSalary(Double salary);

    public Double getAverageSalary();

    public void updateReasons(String reason);

    public List<String> getReasons();
}
