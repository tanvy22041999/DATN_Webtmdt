package com.spring.ecomerce.entities.clone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.ecomerce.entities.inner.Price;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "category")
public class CategoryEntity extends BasicEntity {

    @Id
    private String id;
    private String name;
    private String nameEn;
    private String pathseo;
    private ImageEntity image;

    @Transient
    private List<SpecificationEntity> specifications;
    private List<FilterEntity> filter;
    private List<Price> price;
    private boolean accessories = true;
    private String description;

    @JsonIgnore
    private List<String> specification;

    public CategoryEntity () {
        specifications = new ArrayList<>();
        specification = new ArrayList<>();
        filter = new ArrayList<>();
        price =  new ArrayList<>();
    }

    @Getter
    @Setter
    public class FilterEntity {
        private String query;
        private SpecificationEntity specification;
    }
}
