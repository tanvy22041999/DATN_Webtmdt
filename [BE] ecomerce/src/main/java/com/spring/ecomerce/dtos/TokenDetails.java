package com.spring.ecomerce.dtos;

import com.spring.ecomerce.entities.clone.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails {

    private UserEntity userLogin;

    private String token;

    private long expired;

    private List<String> roles = new ArrayList<>();
}
