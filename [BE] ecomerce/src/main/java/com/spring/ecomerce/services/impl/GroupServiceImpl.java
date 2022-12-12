package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.clone.RegistryGroupDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.entities.clone.GroupEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.GroupRepository;
import com.spring.ecomerce.services.GroupService;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService
{

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Page<GroupEntity> getAll(Integer limit, Integer page, String keyword) throws SystemException {
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

        Page<GroupEntity> results = groupRepository.getAll(queryData, pageable);
        return results;
    }

    @Override
    public GroupEntity findById(String id) {
        Optional<GroupEntity> result = groupRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public GroupEntity addNewGroup(RegistryGroupDTO groupRegistry) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(groupRegistry.getName());
        groupEntity.setProducts(groupRegistry.getProducts());
        return groupRepository.save(groupEntity);
    }

    @Override
    public GroupEntity updateGroup(String id, RegistryGroupDTO updateGroup) {
        GroupEntity groupUpdate = this.findById(id);
        if(groupUpdate != null){
            groupUpdate.setName(updateGroup.getName());
            groupUpdate.setProducts(updateGroup.getProducts());
            return groupRepository.save(groupUpdate);
        }
        return null;
    }

    @Override
    public boolean deleteGroup(String id) {
        Optional<GroupEntity> record = groupRepository.findById(id);
        if(record.isPresent()){
            GroupEntity recordDelete = record.get();
            recordDelete.setDelFlg(1);
            GroupEntity resultDelete = groupRepository.save(recordDelete);
            if(resultDelete != null)
            {
                return true;
            }
        }

        return false;
    }
}
