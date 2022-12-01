package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.commons.CloudinaryService;
import com.spring.ecomerce.dtos.clone.RegistryAdDTO;
import com.spring.ecomerce.entities.clone.AdEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.AdRepository;
import com.spring.ecomerce.services.AdService;
import com.spring.ecomerce.services.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Page<AdEntity> getAll(Integer limit, Integer page, Integer active, Integer status) throws SystemException {
        Pageable pageable = PageRequest.of(page, limit);
        boolean activeQuery = false;
        if(active.intValue() == 1){
            activeQuery = true;
        }

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        queryData.put("active", activeQuery);
        queryData = this.prepareConditionForStatus(queryData, status);
        Page<AdEntity> results = adRepository.getAll(queryData, pageable);
        return results;
    }

    @Override
    public AdEntity findById(String id) {
        Optional<AdEntity> result = adRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public AdEntity addNewAd(RegistryAdDTO adRegistry, MultipartFile image) {
//        upload to cloudinary
        ImageEntity imageEntity = null;
        if(image != null && !StringUtils.isEmpty(image.getName())){
            if(image.getContentType().substring(0,5).equals("image")){
                Map uploadResult = cloudinaryService.uploadImageProduct(image);
                imageEntity = imageService.addNewImage(uploadResult.get("url").toString());
            }
        }

//        save data
        AdEntity newAd = new AdEntity();
        BeanUtils.copyProperties(adRegistry, newAd);
        newAd.setImage(imageEntity);
        return adRepository.save(newAd);
    }

    @Override
    public AdEntity updateAd(String id, RegistryAdDTO updateAd) {
            Optional<AdEntity> recordFound = adRepository.findById(id);
            if(recordFound.isPresent()){
                AdEntity updateEntity = recordFound.get();

                if(updateAd.getName() != null) updateEntity.setName(updateAd.getName());
                if(updateAd.getContent() != null) updateEntity.setContent(updateAd.getContent());
                if(updateAd.getLink() != null) updateEntity.setLink(updateAd.getLink());
                if(updateAd.getActive() != null) updateEntity.setActive(updateAd.getActive());

                //check file to change image
                if(updateAd.getFiles() != null){
                    MultipartFile image = updateAd.getFiles();
                    if(image != null && !StringUtils.isEmpty(image.getName())) {
                        if (image.getContentType().substring(0, 5).equals("image")) {
                            Map resultUpdate = cloudinaryService.uploadImageProduct(image);
                            //Delete old  Image
                            ImageEntity newImage = imageService.addNewImage(resultUpdate.get("url").toString());
                            if(newImage == null){
                                return null;
                            }
                            updateEntity.setImage(newImage);
                        }
                    }
                }
                return adRepository.save(updateEntity);
            }
            return null;
    }

    @Override
    public boolean deleteAd(String id) {
        Optional<AdEntity> record = adRepository.findById(id);
        if(record.isPresent()){
            AdEntity recordDelete = record.get();
            recordDelete.setDelFlg(1);
            AdEntity resultDelete = adRepository.save(recordDelete);
            if(resultDelete != null)
            {
                return true;
            }
        }

        return false;
    }

    private BSONObject prepareConditionForStatus(BSONObject queryData, Integer status){
        if(status != null){
            Map<String, Object> mapCondition1 = new HashMap<>();
            Map<String, Object> mapCondition2 = new HashMap<>();
            Date now = new Date();
            switch (status.intValue()){
                case -1:
                    mapCondition1.put("$gte", now);
                    mapCondition2.put("$gte", now);
                    break;
                case 1:
                    mapCondition1.put("$lte", now);
                    mapCondition2.put("$lte", now);
                    break;
                default:
                    mapCondition1.put("$lte", now);
                    mapCondition2.put("$gte", now);
                    break;
            }
            queryData.put("startAt", mapCondition1);
            queryData.put("endAt", mapCondition2);
        }
        return queryData;
    }
}
