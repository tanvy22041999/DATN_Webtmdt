package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.clone.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistryProductDTO {
    private Boolean active;
    private ImageEntity bigImage;
    private BrandEntity brand;
    private CategoryEntity category;
    private String circumstance;
    private List<ColorEntity> colors;
    private String description;
    private GroupEntity group;
    private List<ImageEntity> image;
    private String included;
    private String name;
    private String pathseo;
    private Float priceMax;
    private Float priceMin;
    private Float realPriceMax;
    private Float realPriceMin;
    private List<SpecificationEntity> specifications;
    private String warrently;
}
