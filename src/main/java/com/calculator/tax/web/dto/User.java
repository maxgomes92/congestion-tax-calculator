package com.calculator.tax.web.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 5, max = 10)
    @Column(unique = true)
    private String username;

    @Length(min = 6, max = 12)
    private String password;


    @CreatedDate
    private Instant CreatedAt;
}
