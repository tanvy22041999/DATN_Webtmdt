package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrySpecificationDTO {
    private String name;

    private List<RegistrySelectorDTO> selections;

}
