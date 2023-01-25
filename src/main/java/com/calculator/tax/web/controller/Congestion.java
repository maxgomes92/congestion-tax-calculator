package com.calculator.tax.web.controller;

import com.calculator.tax.service.CongestionTaxCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(Congestion.BASE_URL)
@RestController
@RequiredArgsConstructor
public class Congestion {

    public static final String BASE_URL = "/api/v1/congestion";

    private final CongestionTaxCalculator congestionTaxCalculatorService;

    @PostMapping
    public Number calculateTax() {
        return congestionTaxCalculatorService.calculateTax();
    }
}
