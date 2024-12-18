package com.imperative.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class LoanRuleRepository {
    Map<String,Object> ruleMap;

    public LoanRuleRepository(){
        ruleMap = new LinkedHashMap<String,Object>();
        ruleMap.put("minimumIncome", 30000);
        ruleMap.put("maximumThreshold", 0.4);
        ruleMap.put("minimumCreditScore", 700);
        ruleMap.put("maximumLoanAmountFactor", 10.0);
    }

    public Integer getMinimumIncome() {
        return (Integer)ruleMap.get("minimumIncome");
    }

    public void setMinimumIncome(Integer minimumIncome) {
       ruleMap.put("minimumIncome", minimumIncome);
    }

    // Getter and Setter for maximumThreshold
    public Double getMaximumThreshold() {
        return (Double)ruleMap.get("maximumThreshold");
    }

    public void setMaximumThreshold(Double maximumThreshold) {
        ruleMap.put("maximumThreshold",maximumThreshold);
    }

    // Getter and Setter for minimumCreditScore
    public Integer getMinimumCreditScore() {
        return (Integer)ruleMap.get("minimumCreditScore");
    }

    public void setMinimumCreditScore(Integer minimumCreditScore) {
        ruleMap.put("minimumCreditScore",minimumCreditScore);
    }

    // Getter and Setter for maximumLoanAmountFactor
    public Double getMaximumLoanAmountFactor() {
        return (Double)ruleMap.get("maximumLoanAmountFactor");
    }

    public void setMaximumLoanAmountFactor(Double maximumLoanAmountFactor) {
        ruleMap.put("maximumLoanAmountFactor",maximumLoanAmountFactor);
    }

    public Boolean loanRuleExists(String name) {
        return ruleMap.containsKey(name);
    }

//     - Add an endpoint to update or view loan rules dynamically.
// - Implement caching for frequently accessed rules.
// - Dockerize the application.
}
