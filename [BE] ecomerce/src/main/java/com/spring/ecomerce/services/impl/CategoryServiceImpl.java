package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.RegistryCategoryDTO;
import com.spring.ecomerce.entities.clone.CategoryEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.CategoryRepository;
import com.spring.ecomerce.services.CategoryService;
import com.spring.ecomerce.services.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SpecificationService specificationService;

    @Override
    public Page<CategoryEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException {
        return null;
    }

    @Override
    public CategoryEntity findById(String id) {
        return null;
    }

    @Override
    public CategoryEntity addNewCate(RegistryCategoryDTO categoryDTO) {
        CategoryEntity newCate = new CategoryEntity();
        if(categoryDTO.getName() != null) newCate.setName(categoryDTO.getName());
        if(categoryDTO.getName_en() != null) newCate.setName_en(categoryDTO.getName_en());
        if(categoryDTO.getPathseo() != null) newCate.setPathseo(categoryDTO.getPathseo());
        if(categoryDTO.getImage() != null) newCate.setImage(categoryDTO.getImage());
        if(categoryDTO.getPrice() != null) newCate.setPrice(categoryDTO.getPrice());
        if(categoryDTO.getAccessories() != null) newCate.setAccessories(categoryDTO.getAccessories());
        if(categoryDTO.getDescription() != null) newCate.setDescription(categoryDTO.getDescription());

        List<SpecificationEntity> specificationApplies = new ArrayList<>();
        if(categoryDTO.getSpecifications() != null){
            for(String specifyId : categoryDTO.getSpecifications()){
                SpecificationEntity specifyData = specificationService.findById(specifyId);
                if(specifyData != null){
                    specificationApplies.add(specifyData);
                }
            }
        }
        newCate.setSpecifications(specificationApplies);

        List<CategoryEntity.Filter> filterApplies = new ArrayList<>();
        if(categoryDTO.getFilter() != null){
            for(CategoryEntity.Filter filter: categoryDTO.getFilter()){
                SpecificationEntity specifyData = specificationService.findById(filter.getId());
                if(specifyData != null){
                    filterApplies.add(filter);
                }
            }
        }
        newCate.setFilter(filterApplies);

        return categoryRepository.save(newCate);
    }

    @Override
    public CategoryEntity updateCate(String id, RegistryBrandDTO updateBrand) {
        return null;
    }

    @Override
    public boolean deleteCate(String id) {
        return false;
    }
}
