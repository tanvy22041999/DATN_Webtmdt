package com.spring.ecomerce.repositories.ChefRepository;

import com.spring.ecomerce.entities.Chef;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepository extends MongoRepository<Chef, String> {
    @Query(value = "{'delFlg' : false}", sort = "{'chefName': -1}")
    List<Chef> fetchAll();
}
