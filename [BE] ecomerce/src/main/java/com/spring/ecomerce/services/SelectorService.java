package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistrySelectorDTO;
import com.spring.ecomerce.entities.clone.SelectorEntity;

public interface SelectorService {
    SelectorEntity findById(String id);
    SelectorEntity addNewSelector(RegistrySelectorDTO selectorDTO);

    boolean deleteSelector(String id);
}
