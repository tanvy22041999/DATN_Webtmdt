package com.spring.ecomerce.services.UserService;

import com.spring.ecomerce.dtos.PasswordDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.dtos.UserDTO;
import com.spring.ecomerce.entities.User;
import com.spring.ecomerce.securities.JwtUserDetails;

public interface UserService {
    User findUserByPhoneNumber(String phoneNumber);

    ServiceResponse<UserDTO> createUser(UserDTO userDTO);

    boolean changePasswordByOTP(String userName, String newPassword);

    boolean changePasswordByLogin(JwtUserDetails userDetails, PasswordDTO passwordDTO);

    boolean updateUser(User user);
}
