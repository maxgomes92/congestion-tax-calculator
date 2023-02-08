package com.calculator.tax.service;

import com.calculator.tax.repository.UserRepository;
import com.calculator.tax.web.dto.CreateAccountRequest;
import com.calculator.tax.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser (CreateAccountRequest request) {
        var user = User.builder().username(request.getUsername()).password(request.getPassword()).CreatedAt(Instant.now()).build();

        return userRepository.save(user);
    }
}
