package com.calculator.tax.web.controller;

import com.calculator.tax.service.ICongestionTaxCalculatorService;
import com.calculator.tax.web.dto.CongestionRequest;
import com.calculator.tax.web.dto.CongestionResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(CongestionController.BASE_URL)
@RestController
@RequiredArgsConstructor
public class CongestionController {

    public static final String BASE_URL = "/api/v1/congestion";

    private final ICongestionTaxCalculatorService congestionTaxCalculatorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CongestionResponse calculateTax(@Valid @RequestBody CongestionRequest request) {
        return congestionTaxCalculatorService.calculateTax(request);
    }
}
