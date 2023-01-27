package com.calculator.tax.service

import com.calculator.tax.web.dto.CongestionRequest
import com.calculator.tax.web.dto.TaxDetails
import com.calculator.tax.web.dto.VehicleTypes
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class CongestionTaxCalculatorServiceTest extends Specification {
    ICongestionTaxCalculatorService service

    void setup() {
        service = new CongestionTaxCalculatorService()
    }

    def "CalculateTax"() {
        given:
        def request = CongestionRequest
                .builder()
                .vehicleType(vehicleType)
                .timestamps(timestamps)
                .build()

        when:
        def result = service.calculateTax(request)

        then:
        result.total == total
        result.details == details

        where:
        vehicleType | timestamps || total | details
        VehicleTypes.car | new HashSet<LocalDateTime>() || 0 | new HashSet<LocalDateTime>()
        VehicleTypes.car | Set.of(LocalDateTime.of(2013, 1, 14, 21, 0, 0)) || 0 | Set.of(TaxDetails.builder().timestamp(LocalDate.of(2013, 1, 14)).value(0).build())
        VehicleTypes.car | Set.of(LocalDateTime.of(2013, 2, 7, 6, 23, 27), LocalDateTime.of(2013, 2, 7, 15, 27, 0)) || 13 | Set.of(TaxDetails.builder().timestamp(LocalDate.of(2013, 2, 7)).value(13).build())
    }
}
