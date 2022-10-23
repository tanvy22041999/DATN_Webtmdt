package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<ImageEntity, String> {
}
