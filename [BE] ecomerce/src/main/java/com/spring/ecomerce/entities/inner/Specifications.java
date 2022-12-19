package com.spring.ecomerce.entities.inner;

import com.spring.ecomerce.dtos.clone.RegistrySelectorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Specifications {
    private List<RegistrySelectorDTO> selections;
    private String id;
    private String name;
}
