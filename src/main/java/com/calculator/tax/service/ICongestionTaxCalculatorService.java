package com.calculator.tax.service;

import com.calculator.tax.web.dto.CongestionRequest;
import com.calculator.tax.web.dto.CongestionResponse;

public interface ICongestionTaxCalculatorService {
    CongestionResponse calculateTax(CongestionRequest request);
}
