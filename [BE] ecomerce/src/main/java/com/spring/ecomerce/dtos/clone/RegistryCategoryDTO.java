package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.clone.CategoryEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistryCategoryDTO {
    private String name;
    private String name_en;
    private String pathseo;
    private ImageEntity image;
    private List<String> specifications;
    private List<CategoryEntity.Filter> filter;
    private List<CategoryEntity.Price> price;
    private Boolean accessories;
    private String description;
}
