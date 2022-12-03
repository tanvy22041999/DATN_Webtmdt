package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.CategoryEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<CategoryEntity> getAll(BSONObject query, Pageable pageable);
}
