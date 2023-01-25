package com.calculator.tax.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CongestionResponse {
    private Number total;
    private Set<TaxDetails> details;
}
