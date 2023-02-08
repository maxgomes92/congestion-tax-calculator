package com.calculator.tax.web.controller;


import com.calculator.tax.service.UserService;
import com.calculator.tax.web.dto.CreateAccountRequest;
import com.calculator.tax.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@Valid @RequestBody CreateAccountRequest request) {
        return userService.createUser(request);
    }
}
