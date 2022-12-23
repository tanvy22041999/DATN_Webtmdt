package com.spring.ecomerce.services.UserService;

import com.spring.ecomerce.dtos.clone.PasswordDTO;
import com.spring.ecomerce.dtos.clone.RegistryUserDTO;
import com.spring.ecomerce.dtos.clone.UpdateUserDTO;
import com.spring.ecomerce.entities.clone.UserEntity;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    Page<UserEntity> getAllUser(String phone, Integer page, Integer limit);
    String validateUserBeforeAdd(RegistryUserDTO userDTO);
    UserEntity findUserByPhoneNumber(String phoneNumber);

   UserEntity createUser(RegistryUserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException;

   UserEntity updateUser(String id, UpdateUserDTO updateUserDTO);

   UserEntity findById(String id);

    UserEntity changePassword(UserEntity userLogin, PasswordDTO passwordDTO);
}
