package com.spring.ecomerce.entities.clone;

import com.spring.ecomerce.entities.inner.Price;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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
    private List<SpecificationEntity> specifications;
    private List<FilterEntity> filter;
    private List<Price> price;
    private boolean accessories = true;
    private String description;

    public CategoryEntity () {
        specifications = new ArrayList<>();
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
