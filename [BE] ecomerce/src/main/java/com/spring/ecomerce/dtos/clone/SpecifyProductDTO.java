package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.clone.SelectorEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecifyProductDTO {
    private String id;
    private String name;
    private String value;
    List<SelectorEntity> selections;
}
