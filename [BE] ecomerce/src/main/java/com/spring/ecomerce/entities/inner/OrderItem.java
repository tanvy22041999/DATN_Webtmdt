package com.spring.ecomerce.entities.inner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.entities.clone.ProductEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Persistent;

@Getter
@Setter
public class OrderItem {
    @JsonIgnore
    private String productId;
    @JsonIgnore
    private String colorId;

    private String name;
    private String nameColor;
    private Double price;
    private ImageEntity image;
    private Integer quantity;

    @Persistent
    private ProductEntity product;
    @Persistent
    private ColorEntity color;
}
