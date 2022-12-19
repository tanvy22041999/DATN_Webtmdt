package com.spring.ecomerce.services.UserService;

import com.spring.ecomerce.dtos.PasswordDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.dtos.UserDTO;
import com.spring.ecomerce.dtos.clone.RegistryUserDTO;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.securities.JwtUserDetails;

public interface UserService {

    String validateUserBeforeAdd(RegistryUserDTO userDTO);
    UserEntity findUserByPhoneNumber(String phoneNumber);

   UserEntity createUser(RegistryUserDTO userDTO);

    boolean changePasswordByOTP(String userName, String newPassword);

    boolean changePasswordByLogin(JwtUserDetails userDetails, PasswordDTO passwordDTO);

    boolean updateUser(UserEntity user);
}
