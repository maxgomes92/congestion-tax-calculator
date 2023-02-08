package com.calculator.tax.repository;

import com.calculator.tax.web.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
