package com.spring.ecomerce.services.UserService;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.PasswordDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.dtos.UserDTO;
import com.spring.ecomerce.dtos.clone.RegistryUserDTO;
import com.spring.ecomerce.entities.Account;
import com.spring.ecomerce.entities.OTP;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.repositories.AccountRepository.AccountRepository;
import com.spring.ecomerce.repositories.OTPRepository.OTPRepository;
import com.spring.ecomerce.repositories.UserRepository.UserRepository;
import com.spring.ecomerce.securities.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private OTPRepository otpRepository;

    @Override
    public String validateUserBeforeAdd(RegistryUserDTO userDTO) {
        String phoneNumber = userDTO.getPhonenumber();
        String password = userDTO.getPassword();

        if(phoneNumber == null || "".equals(phoneNumber)){
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Phone number"});
        }

        if(password == null || "".equals(phoneNumber)){
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Password"});
        }

        UserEntity userDuplicate = this.findUserByPhoneNumber(phoneNumber);
        if(userDuplicate != null){
            return messageManager.getMessage("ERROR_STORED", new String[]{"User"});
        }

        return null;
    }

    @Override
    public UserEntity findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhonenumber(phoneNumber);
    }

    @Override
    @Transactional
    public UserEntity createUser(RegistryUserDTO userDTO) {

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);

        //create account for user
        Account account = new Account();
        account.setPassword(userDTO.getPassword());
        account.setRoles(new ArrayList<>(Collections.singleton("USER")));
        accountRepository.save(account);
        return user;
    }

    @Override
    @Transactional
    public boolean changePasswordByOTP(String userName, String newPassword) {
        if(newPassword == null) return false;
        if("".equals(newPassword)) return false;

        OTP otp = otpRepository.findByPhoneNumberAndType(userName,1);
        if(otp != null){
            if(otp.isActive()){
                Account account = accountRepository.findByPhoneNumber(userName);
                account.setPassword(newPassword);
                accountRepository.save(account);

                otpRepository.delete(otp);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean changePasswordByLogin(JwtUserDetails userDetails, PasswordDTO passwordDTO) {
        if(userDetails.getPassword().equals(passwordDTO.getOldPassword()) && passwordDTO.getNewPassword() != ""){
            Account account = accountRepository.findByPhoneNumber(userDetails.getUsername());
            account.setPassword(passwordDTO.getNewPassword());

            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserEntity user) {
        return userRepository.save(user)==null?false:true;
    }

}
