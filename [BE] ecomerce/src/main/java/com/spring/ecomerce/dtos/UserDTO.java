package com.spring.ecomerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String phoneNumber;

    @Email
    private String email;

    private String fullName;

    private String dateOfBirth;

    private String nick_name;

    private String password;
}
