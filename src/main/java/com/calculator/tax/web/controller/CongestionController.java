package com.calculator.tax.web.controller;

import com.calculator.tax.service.CongestionTaxCalculator;
import com.calculator.tax.web.dto.CongestionRequest;
import com.calculator.tax.web.dto.CongestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(CongestionController.BASE_URL)
@RestController
@RequiredArgsConstructor
public class CongestionController {

    public static final String BASE_URL = "/api/v1/congestion";

    private final CongestionTaxCalculator congestionTaxCalculator;

    @PostMapping
    public CongestionResponse calculateTax(@RequestBody CongestionRequest request) {
        return congestionTaxCalculator.calculateTax(request);
    }
}
