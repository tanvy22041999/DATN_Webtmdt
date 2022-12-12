package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryGroupDTO;
import com.spring.ecomerce.entities.clone.GroupEntity;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.data.domain.Page;

public interface GroupService {
    Page<GroupEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException;

    GroupEntity findById(String id);

    GroupEntity addNewGroup(RegistryGroupDTO groupRegistry);

    GroupEntity updateGroup(String id, RegistryGroupDTO updateGroup);

    boolean deleteGroup(String id);
}
