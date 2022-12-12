package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryColorDTO;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.ColorRepository;
import com.spring.ecomerce.services.ColorService;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Page<ColorEntity> getAll(Integer limit, Integer page) throws SystemException {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);

        return colorRepository.getAll(queryData, pageable);
    }

    @Override
    public ColorEntity findById(String id) {
        Optional<ColorEntity> result = colorRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public ColorEntity addNewColor(RegistryColorDTO colorDTO) {
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setNameEn(colorDTO.getNameEn());
        colorEntity.setNameVn(colorDTO.getNameVn());
        colorEntity.setCode(colorDTO.getCode());
        return colorRepository.save(colorEntity);
    }

    @Override
    public ColorEntity updateColor(String id, RegistryColorDTO updateColor) {
        ColorEntity colorUpdate = this.findById(id);
        if(colorUpdate != null){
            colorUpdate.setNameVn(updateColor.getNameVn());
            colorUpdate.setNameEn(updateColor.getNameEn());
            colorUpdate.setCode(updateColor.getCode());
            return colorRepository.save(colorUpdate);
        }
        return null;
    }

    @Override
    public boolean deleteColor(String id) {
        Optional<ColorEntity> record = colorRepository.findById(id);
        if(record.isPresent()){
            ColorEntity recordDelete = record.get();
            recordDelete.setDelFlg(1);
            ColorEntity resultDelete = colorRepository.save(recordDelete);
            if(resultDelete != null)
            {
                return true;
            }
        }

        return false;
    }
}
