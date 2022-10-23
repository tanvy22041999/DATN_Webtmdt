package com.spring.ecomerce.repositories.TypeRepository;

import com.spring.ecomerce.entities.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TypeRepository extends MongoRepository<Type, String> {
    @Query(value = "{ 'typeName' : { $regex: ?0, $options: 'i' }, 'delFlg' : false}", sort = "{'typeName': -1}")
    List<Type> findTypeByTypeNameNear(String typeName);

    @Query(value = "{'delFlg' : false}", sort = "{'typeName': -1}")
    List<Type> fetchAll();

    @Query(value = "{'typeId' : ?0, 'delFlg' : false}")
    Type findTypeById(String typeId);
}
