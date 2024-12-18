package com.imperative.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRules {
    private Integer minimumSalaryRequired;
    private Double maximumLoanAmountFactor;
    private Integer minimumCreditScoreRequired;
    private Double maximumExistingLoanObligationsFactor;
}
