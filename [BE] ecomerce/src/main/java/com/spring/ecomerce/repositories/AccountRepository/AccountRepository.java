package com.spring.ecomerce.repositories.AccountRepository;

import com.spring.ecomerce.entities.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
  Account findByPhoneNumber(String phoneNumber);
}
