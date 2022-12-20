package com.spring.ecomerce.repositories.AccountRepository;

import com.spring.ecomerce.entities.Account;
import com.spring.ecomerce.entities.clone.NotificationEntity;
import org.bson.BSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
  Account findByPhoneNumber(String phoneNumber);
}
