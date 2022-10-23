package com.spring.ecomerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user-verification")
public class OTP {
    @Id
    private String id;

    @JsonIgnore
    private int otp;

    private String phoneNumber;

    private boolean isActive;

    private int type;

    @JsonIgnore
    LocalDateTime createDate;
}
