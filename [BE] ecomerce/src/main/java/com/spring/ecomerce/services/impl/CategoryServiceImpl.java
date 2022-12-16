package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.RegistryCategoryDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.CategoryEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import com.spring.ecomerce.entities.inner.Filter;
import com.spring.ecomerce.entities.inner.Price;
import com.spring.ecomerce.entities.inner.Specifications;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.CategoryRepository;
import com.spring.ecomerce.services.CategoryService;
import com.spring.ecomerce.services.ImageService;
import com.spring.ecomerce.services.SpecificationService;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private ImageService imageService;

    @Override
    public List<CategoryEntity> getAllCateProductAndSortKey(String keyword) {
        return null;
    }

    @Override
    public Page<CategoryEntity> getAll(Integer limit, Integer page, String keyword, String accessories) throws SystemException {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);

        if(accessories != null && accessories != "" && accessories != "0"){
            queryData.put("accessories", (accessories == "1") ? true: false);
        }
        else if(!"".equals(keyword)){
            keyword = keyword.replace("/[`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/]/gi", "").trim();
            Map<String, Object> queryName = new HashMap<>();
            queryName.put("$regex", ".*" + keyword + ".*");
            queryName.put("$options", "i");

            Map<String, Object> itemQueries = new HashMap<>();
            itemQueries.put("name", queryName);
            itemQueries.put("name_en", queryName);
            queryData.put("$or", itemQueries);
        }

        Page<CategoryEntity> results = categoryRepository.getAll(queryData, pageable);
        return results;
    }

    @Override
    public CategoryEntity findById(String id) {
        Optional<CategoryEntity> result = categoryRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public CategoryEntity addNewCate(RegistryCategoryDTO categoryDTO) {
        CategoryEntity newCate = new CategoryEntity();
        if(categoryDTO.getName() != null) newCate.setName(categoryDTO.getName());
        if(categoryDTO.getNameEn() != null) newCate.setNameEn(categoryDTO.getNameEn());
        if(categoryDTO.getPathseo() != null) newCate.setPathseo(categoryDTO.getPathseo());
        if(categoryDTO.getDescription() != null) newCate.setDescription(categoryDTO.getDescription());

        //handle image
        String imageId = categoryDTO.getImage();
        if(imageId != null){
            ImageEntity imageFound = imageService.findById(imageId);
            if(imageFound != null){
                newCate.setImage(imageFound);
            }
        }

        List<Filter> filters = categoryDTO.getFilter();
        if(filters != null && filters.size() > 0){
            for(Filter filter : filters){
                if(filter.getId() != null){
                    SpecificationEntity specificationFound = specificationService.findById(filter.getId());
                    if(specificationFound != null){
                        CategoryEntity.FilterEntity filterAdd = newCate.new FilterEntity();
                        filterAdd.setSpecification(specificationFound);
                        filterAdd.setQuery(filter.getQuery());
                        newCate.getFilter().add(filterAdd);
                    }
                }
            }
        }

        List<Specifications> specifications = categoryDTO.getSpecifications();
        if(specifications != null && specifications.size() > 0){
            for(Specifications specification : specifications){
                if(specification.getId() != null){
                    SpecificationEntity specificationFound = specificationService.findById(specification.getId());
                    if(specificationFound != null){
                        newCate.getSpecifications().add(specificationFound);
                    }
                }
            }
        }

        List<Price> prices = categoryDTO.getPrice();
        if(prices != null && prices.size() > 0){
            newCate.setPrice(prices);
        }

        return categoryRepository.save(newCate);
    }

    @Override
    public CategoryEntity updateCate(String id, RegistryCategoryDTO categoryDTO) {
        CategoryEntity newCate = new CategoryEntity();
        if(categoryDTO.getName() != null) newCate.setName(categoryDTO.getName());
        if(categoryDTO.getNameEn() != null) newCate.setNameEn(categoryDTO.getNameEn());
        if(categoryDTO.getPathseo() != null) newCate.setPathseo(categoryDTO.getPathseo());
      //  if(categoryDTO.getImage() != null) newCate.setImage(categoryDTO.getImage());
        if(categoryDTO.getPrice() != null) newCate.setPrice(categoryDTO.getPrice());
      //  if(categoryDTO.getAccessories() != null) newCate.setAccessories(categoryDTO.getAccessories());
        if(categoryDTO.getDescription() != null) newCate.setDescription(categoryDTO.getDescription());

        List<SpecificationEntity> specificationApplies = new ArrayList<>();
        if(categoryDTO.getSpecifications() != null){
//            for(String specifyId : categoryDTO.getSpecifications()){
//                SpecificationEntity specifyData = specificationService.findById(specifyId);
//                if(specifyData != null){
//                    specificationApplies.add(specifyData);
//                }
//            }
        }
        newCate.setSpecifications(specificationApplies);

        return categoryRepository.save(newCate);
    }

    @Override
    public boolean deleteCate(String id) {
        Optional<CategoryEntity> record = categoryRepository.findById(id);
        if(record.isPresent()){
            CategoryEntity recordDelete = record.get();
            recordDelete.setDelFlg(1);
            CategoryEntity resultDelete = categoryRepository.save(recordDelete);
            if(resultDelete != null)
            {
                return true;
            }
        }

        return false;
    }
}
