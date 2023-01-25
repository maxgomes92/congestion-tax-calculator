package com.calculator.tax.service;

import com.calculator.tax.web.dto.CongestionRequest;
import com.calculator.tax.web.dto.CongestionResponse;

public interface ICongestionTaxCalculator {
    CongestionResponse calculateTax(CongestionRequest request);
}
