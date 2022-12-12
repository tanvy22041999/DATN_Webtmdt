package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ColorEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ColorRepository extends MongoRepository<ColorEntity, String> {
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<ColorEntity> getAll(BSONObject query, Pageable pageable);
}
