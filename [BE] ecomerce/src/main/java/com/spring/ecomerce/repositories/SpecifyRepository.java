package com.spring.ecomerce.repositories;

import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.SpecificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecifyRepository extends MongoRepository<SpecificationEntity, String> {
}
