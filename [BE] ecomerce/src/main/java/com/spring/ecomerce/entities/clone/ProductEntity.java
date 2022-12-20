package com.spring.ecomerce.entities.clone;

import com.spring.ecomerce.entities.inner.ColorProduct;
import com.spring.ecomerce.entities.inner.SpecifyProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class ProductEntity extends BasicEntity {
    @Id
    private String id;
    private String name;
    private Double priceMax;
    private Double priceMin;
    private Double realPriceMax;
    private Double realPriceMin;
    private int amount;
    private boolean active;
    private String pathseo;
    private String warrently;
    private String circumstance;
    private String included;
    private ImageEntity bigimage;
    private List<ImageEntity> image;
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
}
