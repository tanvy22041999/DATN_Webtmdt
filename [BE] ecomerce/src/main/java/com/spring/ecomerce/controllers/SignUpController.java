package com.spring.ecomerce.controllers;

import com.spring.ecomerce.dtos.OTPDTO;
import com.spring.ecomerce.dtos.UserDTO;
import com.spring.ecomerce.entities.OTP;
import com.spring.ecomerce.entities.User;
import com.spring.ecomerce.entities.response.ResponseData;
import com.spring.ecomerce.services.OTPService.OTPService;
import com.spring.ecomerce.services.UserService.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/rest/sign-up")
public class SignUpController {
    private final UserService userService;

    private final MessageSource messageSource;

    private final OTPService otpService;

    public SignUpController(UserService userService, MessageSource messageSource, OTPService otpService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.otpService = otpService;
    }

    @ApiOperation("Send OTP to client before user signup private information")
    @PostMapping("/send-otp/{phonenumber}")
    public ResponseEntity<ResponseData> sendOTPSignUp(@Valid @PathVariable("phonenumber") String number){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            User user = userService.findUserByPhoneNumber(number);

            //Check account exist
            if(user != null){
                    return new ResponseEntity<>(ResponseData.builder()
                            .success(false)
                            .message(messageSource.getMessage("error.user.exist", null,locale))
                            .data(number)
                            .build(), BAD_REQUEST);
            }

            //Passed if user was vertification before
            OTP otp = otpService.findByPhoneNumberAndType(number,0);
            if(otp != null){
                if(otp.isActive()==true){
                    return new ResponseEntity<>(ResponseData.builder()
                            .success(true)
                            .message(messageSource.getMessage("success.otp-was-veritify-before", null,locale))
                            .data(number)
                            .build(), OK);
                }
            }

            //Send OTP for client
            otpService.sendOTP(number,0);
            return new ResponseEntity<>(ResponseData.builder()
                    .success(true)
                    .message(messageSource.getMessage("success.otp-sent", null,locale))
                    .data(number)
                    .build(), OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.internal", null,locale))
                    .data(number)
                    .build(), BAD_REQUEST);
        }
    }

    @ApiOperation("Activate otp for new user")
    @PostMapping("/activate/{phonenumber}")
    public  ResponseEntity<ResponseData> activateOTPForNewUser(@PathVariable("phonenumber") String phonenumber, @RequestBody OTPDTO otp){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            OTP otpFound = otpService.findByPhoneNumberAndType(phonenumber, 0);
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
                    .data(otp)
                    .build(), BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.internal", null,locale))
                    .data(phonenumber)
                    .build(), BAD_REQUEST);
        }
    }

    @ApiOperation("Sign up information for new user")
    @PostMapping("/information/user")
    public  ResponseEntity<ResponseData> signUpNewUserInformation(@RequestBody UserDTO userDTO){
        Locale locale = LocaleContextHolder.getLocale();
        try{
            OTP otpFound = otpService.findByPhoneNumberAndType(userDTO.getPhoneNumber(),0);
            if(otpFound != null){
                if(otpFound.isActive()){
                    userService.createUser(userDTO);
                    otpService.removeOTP(otpFound);

                    return new ResponseEntity<>(ResponseData.builder()
                            .success(true)
                            .message(messageSource.getMessage("success.user-created", null,locale))
                            .data(userDTO)
                            .build(), OK);
                }
            }
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.user.invalid-verify-otp", null,locale))
                    .data(userDTO)
                    .build(), BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(messageSource.getMessage("error.internal", null,locale))
                    .data(userDTO)
                    .build(), BAD_REQUEST);
        }
    }
}
