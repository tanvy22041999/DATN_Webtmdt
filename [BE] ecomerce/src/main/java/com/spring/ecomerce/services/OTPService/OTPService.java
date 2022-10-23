package com.spring.ecomerce.services.OTPService;

import com.spring.ecomerce.entities.OTP;

import java.text.ParseException;

public interface OTPService {
    void sendOTP( String number, int type) throws ParseException;

    boolean insertOrUpdateOTP(OTP otp,int type);

    OTP findByPhoneNumberAndType(String phonenumber, int type);

    boolean update(OTP otpFound);

    boolean removeOTP(OTP otpFound);
}
