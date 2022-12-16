package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.entities.clone.CategoryEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.repositories.ImageRepository;
import com.spring.ecomerce.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageEntity addNewImage(String url) {
        ImageEntity newImage = new ImageEntity();
        newImage.setPublicUrl(url);
        return imageRepository.save(newImage);
    }

    @Override
    public ImageEntity findById(String id) {
        Optional<ImageEntity> result = imageRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
