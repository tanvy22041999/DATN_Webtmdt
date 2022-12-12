package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.inner.GroupProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistryGroupDTO {
    private String name;
    private List<GroupProduct> products;
}
