package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.inner.Filter;
import com.spring.ecomerce.entities.inner.Price;
import com.spring.ecomerce.entities.inner.Specifications;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistryCategoryDTO {
    private String name;
    private String nameEn;
    private String pathseo;
    private String accessories;
    private String description;
    private String image;
    private List<Price> price;
    private List<Specifications> specifications;
    private List<Filter> filter;

}
