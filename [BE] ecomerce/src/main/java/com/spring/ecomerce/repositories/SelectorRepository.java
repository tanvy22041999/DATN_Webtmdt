package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.SelectorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectorRepository extends MongoRepository<SelectorEntity, String> {
}
