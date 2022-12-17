package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.SpecificationEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecifyRepository extends MongoRepository<SpecificationEntity, String> {
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<SpecificationEntity> getAll(BSONObject query, Pageable pageable);
}
