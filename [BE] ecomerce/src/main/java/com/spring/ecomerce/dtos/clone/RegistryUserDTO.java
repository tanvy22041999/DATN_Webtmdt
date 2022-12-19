package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryUserDTO {
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String password;
    private String email;
    private String address;
}
