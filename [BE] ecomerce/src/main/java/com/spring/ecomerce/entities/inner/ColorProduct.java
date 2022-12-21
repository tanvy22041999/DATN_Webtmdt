package com.spring.ecomerce.entities.inner;

import com.spring.ecomerce.entities.clone.ImageEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ColorProduct {
    @Field("idColor")
    private String id;
    private String nameEn;
    private String nameVn;
    private ImageEntity image;
    private String imageLink;
    private Integer amount;
    private Double realPrice;
    private Double price;
}
