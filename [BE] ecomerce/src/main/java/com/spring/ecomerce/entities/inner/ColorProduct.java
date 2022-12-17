package com.spring.ecomerce.entities.inner;

import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import lombok.Getter;
import lombok.Setter;

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
