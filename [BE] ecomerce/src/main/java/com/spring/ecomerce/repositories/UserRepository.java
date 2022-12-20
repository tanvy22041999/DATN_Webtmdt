package com.spring.ecomerce.repositories;
import com.spring.ecomerce.entities.clone.NotificationEntity;
import com.spring.ecomerce.entities.clone.UserEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByPhonenumber(String phoneNumber);

    @Query(value = "?0", sort = "{'createTimeStamp': -1}")
    Page<UserEntity> getByConditionsForPageable(BSONObject query, Pageable pageable);
}
