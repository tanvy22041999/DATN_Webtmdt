package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.ColorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ColorRepository extends MongoRepository<ColorEntity, String> {
}
