package com.calculator.tax.web.controller

import com.calculator.tax.service.ICongestionTaxCalculatorService
import com.calculator.tax.web.dto.CongestionRequest
import com.calculator.tax.web.dto.CongestionResponse
import com.calculator.tax.web.dto.TaxDetails
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.hamcrest.Matchers.equalTo

@WebMvcTest(CongestionController)
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
            .vehicleType("car")
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
                    jsonPath('$.total', equalTo(result.total))
            )

        then:
        1 * congestionTaxCalculator.calculateTax(_) >> result
    }
}
