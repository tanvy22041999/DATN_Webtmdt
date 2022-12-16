package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.CloudinaryService;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.UploadImageDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.CategoryEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.services.ImageService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private BaseResponseEntity baseResponse;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public String uploadImage(@ModelAttribute UploadImageDTO fileUpload) throws SystemException {
        try{
            Map uploadResult = cloudinaryService.uploadImageBrand(fileUpload.getImage());
            ImageEntity imageEntity = imageService.addNewImage(uploadResult.get("url").toString());
            if(imageEntity == null){
                throw new SystemException();
            }
            List<ImageEntity> imageResponse = new ArrayList<>();
            imageResponse.add(imageEntity);

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("images", imageResponse);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }
}
