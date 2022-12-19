package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.clone.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistryProductDTO {
    private Boolean active;
    private String bigimage;
    private String brand;
    private String category;
    private String circumstance;
    private List<ColorProductDTO> colors;
    private String description;
    private String descText;
    private String group;
    private List<String> image;
    private String included;
    private String name;
    private String pathseo;
    private Integer priceMax;
    private Integer priceMin;
    private Integer realPriceMax;
    private Integer realPriceMin;
    private List<SpecifyProductDTO> specifications;
    private String warrently;
    private int amount;
    private Float weight;
    private Float length;
    private Float height;
    private Float width;
}
