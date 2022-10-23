package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.BrandEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends MongoRepository<BrandEntity, String> {
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<BrandEntity> getAll(BSONObject query, Pageable pageable);

    @Query(value = "{ 'name' : { $regex: ?0, $options: 'i' }}", sort = "{'name': -1}")
    List<BrandEntity> findByBrandNameAndIgnoreCase(String name);
}
