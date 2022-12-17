package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistrySelectorDTO;
import com.spring.ecomerce.dtos.clone.RegistrySpecificationDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.SelectorEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.SpecifyRepository;
import com.spring.ecomerce.services.SelectorService;
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
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecifyRepository specifyRepository;

    @Autowired
    private SelectorService selectorService;

    @Override
    public Page<SpecificationEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        if(!"".equals(keyword)){
            keyword = keyword.replace("/[`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/]/gi", "").trim();
            Map<String, Object> queryName = new HashMap<>();
            queryName.put("$regex", ".*" + keyword + ".*");
            queryName.put("$options", "i");
            queryData.put("name", queryName);
        }

        Page<SpecificationEntity> results = specifyRepository.getAll(queryData, pageable);
        return results;
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
    public SpecificationEntity addNewSpecify(RegistrySpecificationDTO specificationDTO) {
        List<SelectorEntity> selectorsSaved = new ArrayList<>();
        if(specificationDTO.getSelections() != null && specificationDTO.getSelections().size() > 0){
            for(RegistrySelectorDTO selector :  specificationDTO.getSelections()){
                if(selector.getName() != null && !"".equals(selector.getName())){
                    SelectorEntity selectorSaved = selectorService.addNewSelector(selector);
                    if(selectorSaved != null){
                        selectorsSaved.add(selectorSaved);
                    }
                }
            }
        }

        SpecificationEntity specificationEntity = new SpecificationEntity();
        specificationEntity.setName(specificationDTO.getName());
        specificationEntity.setSelections(selectorsSaved);
        return specifyRepository.save(specificationEntity);
    }

    @Override
    public SpecificationEntity updateSpecify(String id, RegistrySpecificationDTO updateSpecification) {
        SpecificationEntity specificationUpdate = this.findById(id);
        if(specificationUpdate == null){
            return null;
        }

        List<SelectorEntity> selectorsSaved = new ArrayList<>();
        if(updateSpecification.getSelections() != null && updateSpecification.getSelections().size() > 0){
            for(RegistrySelectorDTO selector :  updateSpecification.getSelections()){
                if(selector.getName() != null && !"".equals(selector.getName())){
                    if(selector.getId() != null && !"".equals(selector.getId())){
                        SelectorEntity selectorFound = selectorService.findById(id);
                        if(selectorFound != null){
                            selectorFound.setName(selector.getName());
                            selectorsSaved.add(selectorFound);
                        }
                    }
                    else{
                        SelectorEntity selectorSaved = selectorService.addNewSelector(selector);
                        if(selectorSaved != null){
                            selectorsSaved.add(selectorSaved);
                        }
                    }
                }
            }
        }

        specificationUpdate.setSelections(selectorsSaved);
        specificationUpdate.setName(updateSpecification.getName());
        return specifyRepository.save(specificationUpdate);
    }


    @Override
    public boolean deleteSpecify(String id) {
        Optional<SpecificationEntity> record = specifyRepository.findById(id);
        if(record.isPresent()){
            SpecificationEntity recordDelete = record.get();
            recordDelete.setDelFlg(1);
            SpecificationEntity resultDelete = specifyRepository.save(recordDelete);
            if(resultDelete != null)
            {
                return true;
            }
        }

        return false;
    }
}
