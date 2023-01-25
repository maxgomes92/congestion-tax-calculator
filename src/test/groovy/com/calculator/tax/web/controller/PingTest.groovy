package com.calculator.tax.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import spock.lang.Specification

@WebMvcTest(Ping)
class PingTest extends Specification {

    @Autowired
    MockMvc mockMvc;

    void setup() {

    }

    def "Ping"() {
        when:
        var result = mockMvc.perform(get("/ping"))
            .andExpect(status().isOk())
            .andReturn()

        then:
        result.getResponse().getContentAsString() == "OK"
    }
}
