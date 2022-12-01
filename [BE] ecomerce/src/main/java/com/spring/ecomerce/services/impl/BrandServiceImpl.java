package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.arch.service.ObjectConverter;
import com.spring.ecomerce.commons.CloudinaryService;
import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.entities.clone.AdEntity;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.BrandRepository;
import com.spring.ecomerce.services.BrandService;
import com.spring.ecomerce.services.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Page<BrandEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException {
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

        Page<BrandEntity> results = brandRepository.getAll(queryData, pageable);
        return results;
    }

    @Override
    public BrandEntity findById(String id) {
        Optional<BrandEntity> result = brandRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public BrandEntity addNewBrand(RegistryBrandDTO brandRegistry) {
        //check duplicate brand name
        String brandName = brandRegistry.getName();
        MultipartFile image = brandRegistry.getFiles();
        if(brandName != null){
            ImageEntity imageEntity = null;
            List<BrandEntity> brandConflict = brandRepository.findByBrandNameAndIgnoreCase(brandName);
            if(brandConflict.size() == 0) {
                if(image != null && !StringUtils.isEmpty(image.getName())){
                    if(image.getContentType().substring(0,5).equals("image")){
                        Map uploadResult = cloudinaryService.uploadImageBrand(image);
                        imageEntity = imageService.addNewImage(uploadResult.get("url").toString());
                    }
                }

                BrandEntity newBrand = new BrandEntity();
                newBrand.setName(brandName);
                newBrand.setImage(imageEntity);
                return brandRepository.save(newBrand);
            }
        }
        return null;
    }

    @Override
    public BrandEntity updateBrand(String id, RegistryBrandDTO updateBrand) {
        Optional<BrandEntity> recordFound = brandRepository.findById(id);
        if(recordFound.isPresent()){
            BrandEntity updateEntity = recordFound.get();

            if(updateEntity.getName() != null) updateEntity.setName(updateBrand.getName());

            //check file to change image
            if(updateBrand.getFiles() != null){
                MultipartFile image = updateBrand.getFiles();
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
            return brandRepository.save(updateEntity);
        }
        return null;
    }

    @Override
    public boolean deleteBrand(String id) {
        return false;
    }
}
