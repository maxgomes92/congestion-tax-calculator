package com.calculator.tax.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(Congestion.BASE_URL)
@RestController
public class Congestion {

    public static final String BASE_URL = "/api/v1/congestion";

    @PostMapping
    public Number calculateTax() {
        return 2.01;
    }
}
