package com.imperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imperative.dto.LoanRules;
import com.imperative.service.LoanRuleService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/loanRules")
public class LoanRulesController {
    
    private final LoanRuleService loanRuleService;

   @Autowired
   public LoanRulesController(LoanRuleService loanRuleService) {
       this.loanRuleService = loanRuleService;
   }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/getLoanRules")
    public ResponseEntity<LoanRules> getLoanStatistics() {
        log.info("Getting loan statistics");
        return ResponseEntity.ok().body(LoanRules.builder()
        .minimumSalaryRequired(loanRuleService.getMinimumIncome())
        .minimumCreditScoreRequired(loanRuleService.getMinimumCreditScore())
        .maximumLoanAmountFactor(loanRuleService.getMaximumLoanAmountFactor())
        .maximumExistingLoanObligationsFactor(loanRuleService.getMaximumThreshold())
        .build());
   }

   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String>  updateLoanRule(@RequestParam String ruleName, @RequestParam String value) {
        try{
            loanRuleService.updateLoanRule(ruleName, value);
            return ResponseEntity.ok("Rule : " + ruleName + "' updated successfully to value: " + value) ;
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Rule not found: " + e.getMessage()) ;
        }catch(Exception e){
            return ResponseEntity.badRequest().body("An error occurred while updating the rule: " + e.getMessage());
        }
    }

}
