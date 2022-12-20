package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {
}
