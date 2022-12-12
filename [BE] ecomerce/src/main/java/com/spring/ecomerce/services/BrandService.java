package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.data.domain.Page;

public interface BrandService {
    Page<BrandEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException;

    BrandEntity findById(String id);

    BrandEntity addNewBrand(RegistryBrandDTO brandRegistry);

    BrandEntity updateBrand(String id, RegistryBrandDTO updateBrand);

    boolean deleteBrand(String id);
}
