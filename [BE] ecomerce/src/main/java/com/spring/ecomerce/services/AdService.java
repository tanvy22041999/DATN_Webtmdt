package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryAdDTO;
import com.spring.ecomerce.dtos.clone.UpdateAdDTO;
import com.spring.ecomerce.entities.clone.AdEntity;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdService {
    Page<AdEntity> getAll(Integer limit, Integer page, Integer active, Integer status) throws SystemException;

    AdEntity findById(String id);
    AdEntity addNewAd(RegistryAdDTO adRegistry, MultipartFile image);

    AdEntity updateAd(String id,RegistryAdDTO updateAd);

    boolean deleteAd(String id);
}
