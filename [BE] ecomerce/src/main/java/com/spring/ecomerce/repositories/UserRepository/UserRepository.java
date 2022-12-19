package com.spring.ecomerce.repositories.UserRepository;
import com.spring.ecomerce.entities.clone.UserEntity;
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

    @Query(value = "{ 'nick_name' : { $regex: ?0, $options: 'i' }}", sort = "{'nick_name': -1}")
    Page<UserEntity> findUserByNickNameNear(String nickname, Pageable pageable);
}
