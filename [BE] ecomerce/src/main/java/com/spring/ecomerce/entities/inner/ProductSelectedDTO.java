package com.spring.ecomerce.entities.inner;

import com.spring.ecomerce.entities.clone.ProductEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSelectedDTO {
    private ProductEntity product;
    private Integer quantity;
}
