package com.spring.ecomerce.entities.inner;

import com.spring.ecomerce.entities.clone.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupProduct {
    private String name;
    private List<ProductEntity> product;
}
