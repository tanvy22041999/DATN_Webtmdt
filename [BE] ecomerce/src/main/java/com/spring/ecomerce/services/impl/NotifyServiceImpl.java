package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryNofifyDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.entities.clone.NotificationEntity;
import com.spring.ecomerce.repositories.NotifyRepository;
import com.spring.ecomerce.services.ImageService;
import com.spring.ecomerce.services.NotifyService;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private ImageService imageService;


    @Override
    public Page<NotificationEntity> getAllNotify(String user, String admin, Integer type, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        if(!"".equals(user)){
            queryData.put("user", user);
        }else if(!"".equals(admin)){
            queryData.put("user", null);
        }
        queryData.put("type", type);

        Page<NotificationEntity> results = notifyRepository.getByConditionsForPageable(queryData, pageable);
        return results;
    }

    @Override
    public Page<NotificationEntity> getNewestNotify(String user, String admin, Integer type, Integer limit, Integer page) {
        return null;
    }

    @Override
    public NotificationEntity addNewNotify(RegistryNofifyDTO nofifyDTO) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setName(nofifyDTO.getName());
        notificationEntity.setUser(nofifyDTO.getUser());
        notificationEntity.setLink(nofifyDTO.getLink());
        notificationEntity.setContent(nofifyDTO.getContent());
        notificationEntity.setActive(true);
        notificationEntity.setType(nofifyDTO.getType());
        String image = nofifyDTO.getImage();
        if(image != null && !"".equals(image)){
            ImageEntity imageFound = imageService.findById(image);
            notificationEntity.setImage(imageFound);
        }

        return notifyRepository.save(notificationEntity);
    }

    @Override
    public NotificationEntity updateNotify(String id, RegistryNofifyDTO nofifyDTO) {
        return null;
    }

    @Override
    public boolean deleteNotifyById(String id) {
        return false;
    }

    @Override
    public boolean deleteNotifyForUser(String userId) {
        return false;
    }
}
