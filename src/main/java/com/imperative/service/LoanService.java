package com.imperative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imperative.entity.LoanRequest;
import com.imperative.entity.LoanResponse;
import com.imperative.entity.LoanStatistics;
import com.imperative.repository.ILoanRepository;
import com.imperative.repository.LoanRepository;
import com.imperative.repository.LoanRuleRepository;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LoanService {

    private final ILoanRepository loanRepository;
    private final LoanRuleRepository loanRuleRepository;

    @Autowired
   public LoanService(LoanRepository loanRepository,LoanRuleRepository loanRuleRepository) {
       this.loanRepository = loanRepository;
       this.loanRuleRepository = loanRuleRepository;
   }

    public LoanResponse calculateEligibility(LoanRequest request) {
        Double income = request.getMonthlyIncome();
        Double existingLoanObligations = request.getExistingLoanObligations();
        Double requestedLoanAmount = request.getRequestedLoanAmount();
        Integer creditScore = request.getCreditScore();
        String response="";

        if (!isIncomeSufficient(income)) {
            response="Monthly income is below the required minimum.";
            loanRepository.updateReasons(response);
            return buildLoanResponse(false,response , null);
        }
        
        if (!isCreditScoreEligible(creditScore)) {
            response="Credit Score is below the required minimum.";
            loanRepository.updateReasons(response);
            return buildLoanResponse(false,response , null);
        }

        if (!isObligationsExceedingThreshold(existingLoanObligations, income)) {
            response="Existing loan obligations exceed 40% of monthly income.";
            loanRepository.updateReasons(response);
            return buildLoanResponse(false,response , null);
        }
        if (!isLoanAmountWithinLimit(income,requestedLoanAmount )) {
            response="Requested loan amount exceeds the permissible limit.";
            loanRepository.updateReasons(response);
            return buildLoanResponse(false,response , null);
        }
        // Loan is eligible
        loanRepository.updateAverageLoanAmount(requestedLoanAmount);
        loanRepository.updateAverageSalary(income);
        
        return buildLoanResponse(true, null, requestedLoanAmount);
    }

    private LoanResponse buildLoanResponse(boolean eligible, String reason, Double approvedLoanAmount) {
        
        if (!eligible) {
            return LoanResponse.builder()
                    .eligible(false)
                    .response(reason)
                    .build();
        }
        return LoanResponse.builder()
        .eligible(true)
        .approvedLoanAmount(approvedLoanAmount)
        .emi(fetchEmi(approvedLoanAmount))
        .build();
    }

    private Map<String, Double> fetchEmi(Double approvedLoanAmount) {
        Map<String, Double> emiBreakdown = new LinkedHashMap<>();
        emiBreakdown.put("8%", calculateEMI(approvedLoanAmount, 8));
        emiBreakdown.put("10%", calculateEMI(approvedLoanAmount, 10));
        emiBreakdown.put("12%", calculateEMI(approvedLoanAmount, 12));
        return emiBreakdown;
    }

    private Double calculateEMI(double principal, double annualRate) {
        double monthlyRate = annualRate / 12 / 100;
        int loanTenureMonths = 12; // Assuming fixed 12-month loan
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(((principal * monthlyRate * Math.pow(1 + monthlyRate, loanTenureMonths)) /
                (Math.pow(1 + monthlyRate, loanTenureMonths) - 1))));
    }

    private boolean isIncomeSufficient(Double monthlyIncome) {
        return monthlyIncome >= loanRuleRepository.getMinimumIncome();
    }

    private boolean isObligationsExceedingThreshold(Double existingLoanObligations, Double monthlyIncome) {
        return existingLoanObligations < loanRuleRepository.getMaximumThreshold() * monthlyIncome;
    }

    private boolean isCreditScoreEligible(int creditScore) {
        return creditScore >= loanRuleRepository.getMinimumCreditScore();
    }

    private Boolean isLoanAmountWithinLimit(Double monthlyIncome,Double requestedLoanAmount) {
        return requestedLoanAmount < (loanRuleRepository.getMaximumLoanAmountFactor() * monthlyIncome);
    }

    public LoanStatistics getLoanStatistics(){
        return LoanStatistics.builder()
        .averageLoanAmount(loanRepository.getAverageLoanAmount())
        .averageSalary(loanRepository.getAverageSalary())
        .rejectionReasons(loanRepository.getReasons())
        .build();
    }
}
