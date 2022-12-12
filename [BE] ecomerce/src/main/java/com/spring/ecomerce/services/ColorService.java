package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryColorDTO;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.data.domain.Page;

public interface ColorService {
    Page<ColorEntity> getAll(Integer limit, Integer page) throws SystemException;

    ColorEntity findById(String id);

    ColorEntity addNewColor(RegistryColorDTO colorDTO);

    ColorEntity updateColor(String id, RegistryColorDTO updateColor);

    boolean deleteColor(String id);
}
