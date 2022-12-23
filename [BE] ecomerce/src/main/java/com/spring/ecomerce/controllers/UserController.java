package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.TokenDetails;
import com.spring.ecomerce.dtos.clone.PasswordDTO;
import com.spring.ecomerce.dtos.clone.RegistryUserDTO;
import com.spring.ecomerce.dtos.clone.UpdateUserDTO;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.securities.JwtTokenUtils;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.services.OTPService.OTPService;
import com.spring.ecomerce.services.UserService.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BaseResponseEntity baseResponse;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @GetMapping()
    public String getAllUser(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "phone", defaultValue = "") String phone) throws SystemException {
        try {
            Page<UserEntity> results = userService.getAllUser(phone, page, limit);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("limit", limit);
            dataResponse.put("page", page);
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("users", results.getContent());

            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);
        } catch (Exception ex) {
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/signup")
    public String addNewUser(@RequestBody RegistryUserDTO userDTO) throws SystemException {
        try {
            String validateMessage = userService.validateUserBeforeAdd(userDTO);
            if (validateMessage != null) {
                baseResponse.failed(403, validateMessage);
                return baseResponse.getResponseBody();
            }
            UserEntity result = userService.createUser(userDTO);
            if (result == null) {
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
            } else {
                baseResponse.created();
                Map<String, Object> dataResponse = new HashMap<>();
                return baseResponse.getResponseBody(dataResponse);
            }
        } catch (Exception ex) {
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/auth/profile")
    public String getInfoLogin() throws SystemException {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);
            UserEntity userLogin = result.getUserLogin();
            userLogin.setToken("Bearer " + result.getToken());

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("user", userLogin);
            return baseResponse.getResponseBody(dataResponse);
        } catch (Exception ex) {
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable(name = "id", required = false) String id) throws SystemException {
        try {
            UserEntity userFound = userService.findById(id);
            baseResponse.retrieved();
            return baseResponse.getResponseBody(Map.of("user", userFound));
        } catch (Exception ex) {
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/change-pwd")
    public String changePassword(@RequestBody PasswordDTO passwordDTO) throws SystemException {
        try {
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);

            UserEntity userEntity = userService.changePassword(result.getUserLogin(), passwordDTO);
            if(userEntity == null){
                baseResponse.failed(400, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
            }
            else{
                baseResponse.updated();
                return baseResponse.getResponseBody(Map.of("message", messageManager.getMessage("SUCCESS_UPDATE_PASSWORD", null)));
            }
        } catch (Exception ex) {
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
        }
        return baseResponse.getResponseBody();
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable(name = "id", required = false) String id, @RequestBody UpdateUserDTO updateUserDTO) throws SystemException {
        try {
            userService.updateUser(id, updateUserDTO);
            baseResponse.updated();
            return baseResponse.getResponseBody();
        } catch (Exception ex) {
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
        }

        return baseResponse.getResponseBody();
    }
}
