package com.imperative.controller;

import com.imperative.dto.LoanEligible;
import com.imperative.dto.LoanNotEligible;
import com.imperative.entity.LoanRequest;
import com.imperative.entity.LoanResponse;
import com.imperative.entity.LoanStatistics;
import com.imperative.service.LoanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class LoanController {

   private final LoanService loanService;

   @Autowired
   public LoanController(LoanService loanService) {
       this.loanService = loanService;
   }

   @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/loans/eligibility")
    public ResponseEntity<?> checkLoanEligibility(@RequestBody LoanRequest loanRequest) {

            log.info("Received loan Approval request : {}", loanRequest);
            LoanResponse loanResponse = loanService.calculateEligibility(loanRequest);
            if(loanResponse.getEligible()){
                log.info("Loan Approved : {}", loanResponse);
                return ResponseEntity.ok(LoanEligible.builder()
                .eligible(loanResponse.getEligible())
                .approvedLoanAmount(loanResponse.getApprovedLoanAmount())
                .emi(loanResponse.getEmi())
                .build());
            }else{
                log.warn("Reason: {}", loanResponse.getResponse());
                LoanNotEligible loanNotEligibleResponse = LoanNotEligible.builder()
                .eligible(loanResponse.getEligible())
                .response(loanResponse.getResponse())
                .build();
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(loanNotEligibleResponse);
            }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/loan-statistics")
    public ResponseEntity<LoanStatistics> loanStatistics(){
        log.info("Received loan Statistics request");
        return ResponseEntity.ok().body(loanService.getLoanStatistics());
    }
            
            
}
