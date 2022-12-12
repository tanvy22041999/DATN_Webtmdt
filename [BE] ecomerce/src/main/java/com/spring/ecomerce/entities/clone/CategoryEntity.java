package com.spring.ecomerce.entities.clone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "category")
public class CategoryEntity extends BasicEntity {
    @Id
    private String id;

    private String name;
    private String nameEn;
    private String pathseo;
    private ImageEntity image;
    private List<SpecificationEntity> specifications;
    private List<SpecificationEntity> filter;
    private List<Price> price;
    private boolean accessories = true;
    private String description;

    @Getter
    @Setter
    public class Filter {
        private String id;
        private String query;
    }

    @Getter
    @Setter
    public class Price{
        private String name;
        private Integer min;
        private Integer max;
    }
}
