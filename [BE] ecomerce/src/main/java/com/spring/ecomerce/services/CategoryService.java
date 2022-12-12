package com.spring.ecomerce.services;
import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.RegistryCategoryDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.CategoryEntity;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAllCateProductAndSortKey(String keyword);
    Page<CategoryEntity> getAll(Integer limit, Integer page, String keyword, String accessories) throws SystemException;

    CategoryEntity findById(String id);

    CategoryEntity addNewCate(RegistryCategoryDTO categoryDTO);

    CategoryEntity updateCate(String id, RegistryCategoryDTO categoryDTO);

    boolean deleteCate(String id);
}
