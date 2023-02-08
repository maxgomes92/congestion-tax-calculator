package com.calculator.tax.web.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @Length(min = 5, max = 10)
    private String username;

    @Length(min = 6, max = 12)
    private String password;
}
