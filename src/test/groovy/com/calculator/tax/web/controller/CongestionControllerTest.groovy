package com.calculator.tax.web.controller

import com.calculator.tax.configuration.DateMapper
import com.calculator.tax.service.ICongestionTaxCalculatorService
import com.calculator.tax.web.dto.CongestionRequest
import com.calculator.tax.web.dto.CongestionResponse
import com.calculator.tax.web.dto.TaxDetails
import com.calculator.tax.web.dto.VehicleTypes
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.LocalDate

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.hamcrest.Matchers.equalTo

@WebMvcTest(CongestionController)
@Import(DateMapper.class)
class CongestionControllerTest extends Specification {
    @SpringBean
    ICongestionTaxCalculatorService congestionTaxCalculator = Mock(ICongestionTaxCalculatorService)

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    def "Calculate Tax"() {
        given:
        def request = CongestionRequest
            .builder()
            .vehicleType(VehicleTypes.car)
            .timestamps(Set.of(LocalDateTime.now()))
            .build()

        def result = CongestionResponse
            .builder()
            .total(1000)
            .details(Set.of(TaxDetails.builder().value(900).timestamp(LocalDate.now()).build()))
            .build()

        def postToPerform = post(CongestionController.BASE_URL)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)

        when:
        mockMvc.perform(postToPerform)
            .andExpect(status().isOk())
            .andExpectAll(
                    jsonPath('$.total', equalTo(result.total)),
                    jsonPath('$.details', hasSize(result.getDetails().size())),
                    jsonPath('$.details[0].timestamp', equalTo(result.getDetails()[0].getTimestamp().toString())),
                    jsonPath('$.details[0].value', equalTo(result.getDetails()[0].getValue())),
            )

        then:
        1 * congestionTaxCalculator.calculateTax(_) >> result
    }

    def "Test arguments"() {
        given:
        def postToPerform = post(CongestionController.BASE_URL)
            .content(content)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)

        expect:
        mockMvc.perform(postToPerform)
            .andExpect(status().is(statusCode))

        where:
        content || statusCode
        "{ \"vehicleType\": \"car\" }"              || 200 // Valid type, no timestamp
        "{ \"vehicleType\": \"military\" }"              || 200 // Valid type, no timestamp
        "{ \"vehicleType\": \"car\", \"timestamps\": [\"some_invalid_date\"] }" || 400 // Valid type, invalid timestamp
        "{ \"vehicleType\": \"car\", \"timestamps\": [\"2013-01-14 21:00:00\"] }" || 200 // Valid type, valid timestamp
        "{ \"vehicleType\": \"not_valid_type\" }"   || 400 // Invalid type, no timestamp
    }
}
