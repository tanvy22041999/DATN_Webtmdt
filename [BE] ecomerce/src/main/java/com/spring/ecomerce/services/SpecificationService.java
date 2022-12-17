package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.RegistrySpecificationDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.data.domain.Page;

public interface SpecificationService {
    Page<SpecificationEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException;

    SpecificationEntity findById(String id);

    SpecificationEntity addNewSpecify(RegistrySpecificationDTO specificationDTO);

    SpecificationEntity updateSpecify(String id, RegistrySpecificationDTO updateSpecification);

    boolean deleteSpecify(String id);
}
