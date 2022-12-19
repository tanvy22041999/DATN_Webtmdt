package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDTO {
    private String phonenumber;

    private String password;
}
