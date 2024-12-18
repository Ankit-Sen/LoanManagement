package com.imperative.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanStatistics {
    private Double averageLoanAmount;
    private Double averageSalary;
    private List<String> rejectionReasons;
}
