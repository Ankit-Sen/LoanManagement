package com.imperative.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanEligible {
    private Boolean eligible;
    private Double approvedLoanAmount;
    private Map<String,Double> emi;
}
