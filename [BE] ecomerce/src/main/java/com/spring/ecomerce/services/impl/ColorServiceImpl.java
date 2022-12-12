package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryColorDTO;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.ColorRepository;
import com.spring.ecomerce.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Page<ColorEntity> getAll(Integer limit, Integer page) throws SystemException {
        Pageable pageable = PageRequest.of(page, limit);

        return colorRepository.findAll(pageable);
    }

    @Override
    public ColorEntity findById(String id) {
        return null;
    }

    @Override
    public ColorEntity addNewColor(RegistryColorDTO colorDTO) {
        return null;
    }

    @Override
    public ColorEntity updateColor(String id, RegistryColorDTO updateColor) {
        return null;
    }

    @Override
    public boolean deleteColor(String id) {
        return false;
    }
}
