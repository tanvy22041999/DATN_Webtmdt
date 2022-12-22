package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.OrderEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<OrderEntity> getAll(BSONObject query, Pageable pageable);
}
