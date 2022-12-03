package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.SpecifyRepository;
import com.spring.ecomerce.services.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecifyRepository specifyRepository;

    @Override
    public Page<SpecificationEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException {
        return null;
    }

    @Override
    public SpecificationEntity findById(String id) {
        Optional<SpecificationEntity> result = specifyRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public BrandEntity addNewSpecify(RegistryBrandDTO brandRegistry) {
        return null;
    }

    @Override
    public BrandEntity updateSpecify(String id, RegistryBrandDTO updateBrand) {
        return null;
    }

    @Override
    public boolean deleteSpecify(String id) {
        return false;
    }
}
