package com.imperative.entity;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponse {
    private Boolean eligible;
    private Double approvedLoanAmount;
    private Map<String,Double> emi;
    private String response;
}
