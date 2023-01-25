package com.calculator.tax.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaxDetails {
    private LocalDateTime timestamp;
    private Number value;
}
