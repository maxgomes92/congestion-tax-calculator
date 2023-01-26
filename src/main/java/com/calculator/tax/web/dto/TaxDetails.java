package com.calculator.tax.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaxDetails {
    private LocalDate timestamp;
    private Number value;
}
