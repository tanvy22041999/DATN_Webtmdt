package com.spring.ecomerce.repositories.OTPRepository;

import com.spring.ecomerce.entities.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface OTPRepository extends MongoRepository<OTP, UUID> {
    OTP findByPhoneNumber(String phoneNumber);

    @Query(value = "{$and:[{'phoneNumber': ?0},{'type': ?1}]}")
    OTP findByPhoneNumberAndType(String phonenumber, int otp);
}

