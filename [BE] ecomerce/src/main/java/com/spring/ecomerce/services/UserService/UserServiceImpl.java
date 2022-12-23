package com.spring.ecomerce.services.UserService;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.PasswordDTO;
import com.spring.ecomerce.dtos.clone.RegistryUserDTO;
import com.spring.ecomerce.dtos.clone.UpdateUserDTO;
import com.spring.ecomerce.entities.clone.ImageEntity;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.repositories.AccountRepository.AccountRepository;
import com.spring.ecomerce.repositories.OTPRepository.OTPRepository;
import com.spring.ecomerce.repositories.UserRepository;
import com.spring.ecomerce.services.ImageService;
import com.spring.ecomerce.utils.EncodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public Page<UserEntity> getAllUser(String phone, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        if (!"".equals(phone)) {
            Map<String, Object> queryName = new HashMap<>();
            queryName.put("$regex", ".*" + phone + ".*");
            queryData.put("name", queryName);
        }
        return userRepository.getByConditionsForPageable(queryData, pageable);
    }

    @Override
    public String validateUserBeforeAdd(RegistryUserDTO userDTO) {
        String phoneNumber = userDTO.getPhonenumber();
        String password = userDTO.getPassword();

        if (phoneNumber == null || "".equals(phoneNumber)) {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Phone number"});
        }

        if (password == null || "".equals(phoneNumber)) {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Password"});
        }

        UserEntity userDuplicate = this.findUserByPhoneNumber(phoneNumber);
        if (userDuplicate != null) {
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
    public UserEntity createUser(RegistryUserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password = EncodeUtils.getPasswordHash(userDTO.getPassword(), "SHA1");
        if ("".equals(password)) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstname(userDTO.getFirstname());
        userEntity.setLastname(userDTO.getLastname());
        userEntity.setPhonenumber(userDTO.getPhonenumber());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(password);
        userEntity.setAuthType("local");
        userEntity.setConfirmed(true);
        userEntity.setRole("1");
        userEntity.setHistory(new ArrayList<>());
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUser(String id, UpdateUserDTO updateUserDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity userFound = userOptional.get();
            if (updateUserDTO.getImage() != null) {
                ImageEntity imageUser = imageService.findById(updateUserDTO.getImage());
                if (imageUser != null) {
                    userFound.setImage(imageUser);
                }
            }

            if (updateUserDTO.getAddress() != null && !"".equals(updateUserDTO.getAddress())) {
                userFound.setAddress(updateUserDTO.getAddress());
            }

            if (updateUserDTO.getLastname() != null && !"".equals(updateUserDTO.getLastname())) {
                userFound.setLastname(updateUserDTO.getLastname());
            }

            if (updateUserDTO.getFirstname() != null && !"".equals(updateUserDTO.getFirstname())) {
                userFound.setFirstname(updateUserDTO.getFirstname());
            }

            if (updateUserDTO.getEmail() != null && !"".equals(updateUserDTO.getEmail())) {
                userFound.setEmail(updateUserDTO.getEmail());
            }

            if(updateUserDTO.getRole() != null && !"".equals(updateUserDTO.getRole())){
                userFound.setRole(updateUserDTO.getRole());
            }

            return userRepository.save(userFound);
        }

        return null;
    }

    @Override
    public UserEntity findById(String id) {
        Optional<UserEntity> userOption = userRepository.findById(id);
        if (userOption.isPresent()) {
            return userOption.get();
        }
        return null;
    }

    @Override
    public UserEntity changePassword(UserEntity userLogin, PasswordDTO passwordDTO) {
        if (passwordDTO.getPassword() != null && !"".equals(passwordDTO.getPassword()) && passwordDTO.getNew_password() != null
                && !"".equals(passwordDTO.getNew_password()) && !passwordDTO.getNew_password().equals(passwordDTO.getPassword())) {
            String password = EncodeUtils.getPasswordHash(passwordDTO.getPassword(), "SHA1");
            if(password != null && password.equals(userLogin.getPassword())){
                userLogin.setPassword(EncodeUtils.getPasswordHash(passwordDTO.getNew_password(), "SHA1"));
                return userRepository.save(userLogin);
            }
        }
        return null;
    }
}
