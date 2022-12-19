package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorProductDTO {
    private String id;
    private int amount;
    private Double price;
    private Double realPrice;
    private String image;
    private String nameVn;
    private String imageLink;
}
