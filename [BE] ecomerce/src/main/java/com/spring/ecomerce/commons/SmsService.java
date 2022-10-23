package com.spring.ecomerce.commons;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SmsService {
    @Value(value = "${twilio.account-sid}")
    private String ACCOUNT_SID;

    @Value(value = "${twilio.account-password}")
    private String AUTH_TOKEN ;

    @Value(value = "${twilio.account-from-number}")
    private String FROM_NUMBER;

    private final String  TOPIC_DESTINATION = "/lesson/sms";

    public boolean sendMessageOTP(String phone_number, int otp){
        try{
            this.send(phone_number,otp);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public void send(String phone_number, int otp) throws ParseException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String msg ="Mã OTP của bạn là: "+otp;


        Message message = Message.creator(new PhoneNumber(phone_number), new PhoneNumber(FROM_NUMBER), msg)
                .create();
    }

    public int generateOTP(){
        int min = 100000;
        int max = 999999;
        return  (int)(Math.random()*(max-min+1)+min);
    }

    private String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}
