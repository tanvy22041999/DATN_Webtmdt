package com.spring.ecomerce.controllers;

import com.spring.ecomerce.commons.CloudinaryService;
import com.spring.ecomerce.dtos.OTPDTO;
import com.spring.ecomerce.dtos.PasswordDTO;
import com.spring.ecomerce.entities.OTP;
import com.spring.ecomerce.entities.User;
import com.spring.ecomerce.entities.response.ResponseData;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.services.OTPService.OTPService;
import com.spring.ecomerce.services.UserService.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/rest/user")
public class UserController {

    private final UserService userService;

    private final OTPService otpService;

    private final MessageSource messageSource;

    private final CloudinaryService cloudinaryService;

    private ResponseEntity<ResponseData> responseDataResponseEntity;

    public UserController(UserService userService, OTPService otpService, MessageSource messageSource, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.otpService = otpService;
        this.messageSource = messageSource;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/information")
    public ResponseEntity<ResponseData> getInformationUser(){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findUserByPhoneNumber(userDetails.getUsername());
            return new ResponseEntity<>(ResponseData.builder()
                    .success(true)
                    .message(messageSource.getMessage("success.user-information", null,locale))
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
                       .message(messageSource.getMessage("success.user-password-changed", null,locale))
                       .data(null)
                       .build(), OK);
           }
           return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.user.wrong-password", null,locale))
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
            User user = userService.findUserByPhoneNumber(number);

            //Check account exist
            if(user == null){
                return new ResponseEntity<>(ResponseData.builder()
                        .success(false)
                        .message(messageSource.getMessage("error.user.invalid-verify-otp", null,locale))
                        .data(number)
                        .build(), BAD_REQUEST);
            }

            otpService.sendOTP(number,1);
            return new ResponseEntity<>(ResponseData.builder()
                    .success(true)
                    .message(messageSource.getMessage("success.otp-sent", null,locale))
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
                            .message(messageSource.getMessage("success.otp-verified", null,locale))
                            .data(otp)
                            .build(), OK);
                }
            }
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.user.invalid-verify-otp", null,locale))
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
                        .message(messageSource.getMessage("success.user-password-changed", null,locale))
                        .data(number)
                        .build(), OK);
            }
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.user.invalid-verify-otp", null,locale))
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

    @PostMapping("/upload-image-profile")
    public ResponseEntity<ResponseData> uploadImageProfile(@RequestParam MultipartFile image_profile) throws IOException {
        if(!StringUtils.isEmpty(image_profile.getName())){
            if(image_profile.getContentType().substring(0,5).equals("image")){
                JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User currentUser = userService.findUserByPhoneNumber(userDetails.getUsername());
                if(!StringUtils.isEmpty(currentUser.getUrlImage())){
                    cloudinaryService.deleteImageProfile(currentUser.getImage_public_id());
                }

                Map uploadResult = cloudinaryService.uploadImageProfile(image_profile);
                currentUser.setUrlImage(uploadResult.get("url").toString());
                currentUser.setImage_public_id(uploadResult.get("public_id").toString());
                userService.updateUser(currentUser);

                return new ResponseEntity<>(ResponseData.builder()
                        .success(true)
                        .message("Image profile was change")
                        .data(currentUser)
                        .build(), OK);
            }
            else
            {
                return new ResponseEntity<>(ResponseData.builder()
                        .success(false)
                        .message("The file did not a image file")
                        .data(null)
                        .build(), BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message("The request has file is empty")
                    .data(null)
                    .build(), BAD_REQUEST);
        }
    }
}
