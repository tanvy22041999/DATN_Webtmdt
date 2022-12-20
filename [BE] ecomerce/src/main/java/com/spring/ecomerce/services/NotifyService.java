package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryNofifyDTO;
import com.spring.ecomerce.entities.clone.NotificationEntity;
import org.springframework.data.domain.Page;

public interface NotifyService {
    Page<NotificationEntity> getAllNotify(String user, String admin, Integer type, Integer limit, Integer page);

    NotificationEntity addNewNotify(RegistryNofifyDTO nofifyDTO);

    NotificationEntity updateNotify(String id, RegistryNofifyDTO nofifyDTO);

    boolean deleteNotifyById(String id);

    boolean deleteNotifyForUser(String userId);
}
