package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.OTPDTO;
import com.spring.ecomerce.dtos.PasswordDTO;
import com.spring.ecomerce.dtos.TokenDetails;
import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.RegistryUserDTO;
import com.spring.ecomerce.entities.OTP;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.entities.response.ResponseData;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.securities.JwtTokenUtils;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.services.OTPService.OTPService;
import com.spring.ecomerce.services.UserService.UserService;
import lombok.Getter;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

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
    private OTPService otpService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @GetMapping()
    public String getAllUser(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "phone", defaultValue="") String phone) throws SystemException {
        try{
            Page<UserEntity> results = userService.getAllUser(phone,page,limit);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("limit", limit);
            dataResponse.put("page", page);
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("users", results.getContent());

            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/signup")
    public String addNewUser(@RequestBody RegistryUserDTO userDTO) throws SystemException {
        try{
            String validateMessage = userService.validateUserBeforeAdd(userDTO);
            if(validateMessage != null){
                baseResponse.failed(403, validateMessage);
                return baseResponse.getResponseBody();
            }
            UserEntity result = userService.createUser(userDTO);
            if(result == null){
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
            }
            else {
                baseResponse.created();
                Map<String, Object> dataResponse = new HashMap<>();
                return baseResponse.getResponseBody(dataResponse);
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/auth/profile")
    public String getInfoLogin() throws SystemException {
        try{
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);
            UserEntity userLogin = result.getUserLogin();
            userLogin.setToken("Bearer " + result.getToken());

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("user", userLogin);
            return baseResponse.getResponseBody(dataResponse);
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/information")
    public ResponseEntity<ResponseData> getInformationUser(){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserEntity user = userService.findUserByPhoneNumber(userDetails.getUsername());
            return new ResponseEntity<>(ResponseData.builder()
                    .success(true)
                    .data(user)
                    .build(), OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .data(null)
                    .build(), BAD_REQUEST);
        }
    }

    @GetMapping("/change-password")
    public ResponseEntity<ResponseData> getInformationUser(@Valid @RequestBody PasswordDTO passwordDTO){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
           if(userService.changePasswordByLogin(userDetails, passwordDTO)){
               return new ResponseEntity<>(ResponseData.builder()
                       .success(true)
                       .data(null)
                       .build(), OK);
           }
           return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .data(null)
                    .build(), BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .data(null)
                    .build(), BAD_REQUEST);
        }
    }

    @GetMapping("/common/forgot-password/{phonenumber}")
    public ResponseEntity<ResponseData> sendOTPForgotPassword(@Valid @PathVariable("phonenumber") String number){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            UserEntity user = userService.findUserByPhoneNumber(number);

            //Check account exist
            if(user == null){
                return new ResponseEntity<>(ResponseData.builder()
                        .success(false)
                        .data(number)
                        .build(), BAD_REQUEST);
            }

            otpService.sendOTP(number,1);
            return new ResponseEntity<>(ResponseData.builder()
                    .success(true)
                    .data(number)
                    .build(), OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .data(null)
                    .build(), BAD_REQUEST);
        }
    }

    @PostMapping("/common/active-otp-change-password/{phonenumber}")
    public ResponseEntity<ResponseData> activeOTPChangePassword(@Valid @PathVariable("phonenumber") String phonenumber, @RequestBody OTPDTO otp){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            OTP otpFound = otpService.findByPhoneNumberAndType(phonenumber,1);
            //Check account exist
            if(otpFound != null){
                if(otpFound.getOtp() == otp.getOtp() && !otpFound.isActive()) {
                    //Cập nhật OTP
                    otpFound.setActive(true);
                    otpService.update(otpFound);
                    return new ResponseEntity<>(ResponseData.builder()
                            .success(true)
                            .data(otp)
                            .build(), OK);
                }
            }
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .data(phonenumber)
                    .build(), BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .data(null)
                    .build(), BAD_REQUEST);
        }
    }

    @GetMapping("/common/change-password-by-otp/{phonenumber}")
    public ResponseEntity<ResponseData> changePasswordByOTP(@Valid @PathVariable("phonenumber") String number,@Valid @RequestBody PasswordDTO passwordDTO){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            if(userService.changePasswordByOTP(number, passwordDTO.getNewPassword())){
                return new ResponseEntity<>(ResponseData.builder()
                        .success(true)
                        .data(number)
                        .build(), OK);
            }
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .data(number)
                    .build(), BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .data(null)
                    .build(), BAD_REQUEST);
        }
    }
}
