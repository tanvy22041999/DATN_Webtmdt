package com.spring.ecomerce.entities.clone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class ProductEntity extends BasicEntity {
    private String name;
    private Double priceMax;
    private Double priceMin;
    private Double realPriceMax;
    private Double realPriceMin;
    private Integer amount;
    private boolean active;
    private String pathseo;
    private String warrently;
    private String circumstance;
    private String included;
    private ImageEntity bigimage;
    private CategoryEntity category;
    private BrandEntity brand;
    private List<SpecifyProduct> specifications;
    private String description;
    private String descText;
    private List<ColorProduct> colors;
    private GroupEntity group;
    private Float stars;
    private Float reviewCount;
    private Float weight;
    private Float length;
    private Float height;
    private Float width;


    @Getter
    @Setter
    public class SpecifyProduct {
        private SpecificationEntity specification;

        private String name;

        private String value;

        private List<SelectorEntity> selection;
    }

    @Getter
    @Setter
    public class ColorProduct {
        private ColorEntity color;
        private String nameEn;
        private String nameVn;
        private ImageEntity image;
        private String imageLink;
        private Integer amount;
        private Double realPrice;
        private Double price;
    }
}
