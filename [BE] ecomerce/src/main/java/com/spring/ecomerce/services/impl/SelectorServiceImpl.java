package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistrySelectorDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.entities.clone.SelectorEntity;
import com.spring.ecomerce.repositories.SelectorRepository;
import com.spring.ecomerce.services.SelectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SelectorServiceImpl implements SelectorService {
    @Autowired
    private SelectorRepository selectorRepository;

    @Override
    public SelectorEntity findById(String id) {
        Optional<SelectorEntity> result = selectorRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public SelectorEntity addNewSelector(RegistrySelectorDTO selectorDTO) {
        if(selectorDTO.getName() != null){
            SelectorEntity selectorEntity = new SelectorEntity();
            selectorEntity.setName(selectorDTO.getName());
            return selectorRepository.save(selectorEntity);
        }
        return null;
    }

    @Override
    public boolean deleteSelector(String id) {
        Optional<SelectorEntity> record = selectorRepository.findById(id);
        if(record.isPresent()){
            SelectorEntity recordDelete = record.get();
            recordDelete.setDelFlg(1);
            SelectorEntity resultDelete = selectorRepository.save(recordDelete);
            if(resultDelete != null)
            {
                return true;
            }
        }
        return false;
    }
}
