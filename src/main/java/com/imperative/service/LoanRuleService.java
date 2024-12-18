package com.imperative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imperative.repository.LoanRuleRepository;

@Service
public class LoanRuleService {
    
    private final LoanRuleRepository loanRuleRepository;

    @Autowired
   public LoanRuleService(LoanRuleRepository loanRuleRepository) {
       this.loanRuleRepository = loanRuleRepository;
   }

   public Integer getMinimumIncome() {
    return loanRuleRepository.getMinimumIncome();
}

public void updateMinimumIncome(Integer minimumIncome) {
    loanRuleRepository.setMinimumIncome(minimumIncome);
}

// Getter and Setter for maximumThreshold
public Double getMaximumThreshold() {
    return loanRuleRepository.getMaximumThreshold();
}

public void updateMaximumThreshold(Double maximumThreshold) {
    loanRuleRepository.setMaximumThreshold(maximumThreshold/100);;
}

// Getter and Setter for minimumCreditScore
public Integer getMinimumCreditScore() {
    return loanRuleRepository.getMinimumCreditScore();
}

public void updateMinimumCreditScore(Integer minimumCreditScore) {
    loanRuleRepository.setMinimumCreditScore(minimumCreditScore);
}

// Getter and Setter for maximumLoanAmountFactor
public Double getMaximumLoanAmountFactor() {
    return loanRuleRepository.getMaximumLoanAmountFactor();
}

public void updateMaximumLoanAmountFactor(Double maximumLoanAmountFactor) {
    loanRuleRepository.setMaximumLoanAmountFactor(maximumLoanAmountFactor);
}

public Boolean loanRuleExists(String name) {
    return loanRuleRepository.loanRuleExists(name);
}

public void updateLoanRule(String existingRule, String value){
    if(existingRule.equalsIgnoreCase("minimumIncome")){
        updateMinimumIncome(Integer.parseInt(value));
    }else if(existingRule.equalsIgnoreCase("maximumThreshold")){
        updateMaximumThreshold(Double.parseDouble(value));
    }else if(existingRule.equalsIgnoreCase("minimumCreditScore")){
        updateMinimumCreditScore(Integer.parseInt(value));
    } else if(existingRule.equalsIgnoreCase("maximumLoanAmountFactor")){
        updateMaximumLoanAmountFactor(Double.parseDouble(value));
    } else {
        throw new IllegalArgumentException("Invalid rule name");
    }
}
}
