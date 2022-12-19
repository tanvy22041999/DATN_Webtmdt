package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.ProductEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<ProductEntity> getByConditionsForPageable(BSONObject query, Pageable pageable);

    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    List<ProductEntity> getByConditionsForList(BSONObject query);
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    ProductEntity findProductByKeyPairAndIgnoredCase(BSONObject query);
}
