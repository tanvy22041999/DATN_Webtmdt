package com.spring.ecomerce.entities.inner;

import com.spring.ecomerce.entities.clone.SelectorEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecifyProduct {
    private SpecificationEntity specification;

    private String name;

    private String value;

    private List<SelectorEntity> selection;
}
