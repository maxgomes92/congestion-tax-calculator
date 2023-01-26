package com.calculator.tax.web.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CongestionRequest {

    private VehicleTypes vehicleType;
    private Set<LocalDateTime> timestamps;
}
