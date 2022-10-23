package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.AdEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends MongoRepository<AdEntity, String> {
    @Query(value = "?0", sort = "{'startAt': -1}")
    Page<AdEntity> getAll(BSONObject query, Pageable pageable);
}
