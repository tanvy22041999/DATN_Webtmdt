package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private String address;
    private String email;
    private String firstname;
    private String lastname;
    private String image;
    private String role;
}
