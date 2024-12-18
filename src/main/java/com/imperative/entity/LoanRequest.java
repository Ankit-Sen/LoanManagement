package com.imperative.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private String userId;
    private Double monthlyIncome;
    private Double existingLoanObligations;
    private Integer creditScore;
    private Double requestedLoanAmount;
}
