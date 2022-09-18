package com.example.currency.controller;


import com.example.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/getData/{startDate}/{endDate}")
    public ResponseEntity<?> getData(@PathVariable String startDate, @PathVariable String endDate) {
        return currencyService.splitData(startDate, endDate);
    }
}
