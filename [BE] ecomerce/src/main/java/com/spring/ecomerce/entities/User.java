package com.spring.ecomerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @Id
    private String userId;

    private String phoneNumber;

    @Email
    private String email;

    private String fullName;

    private String dateOfBirth;

    private String code_register_socket;

    private String urlImage;

    private boolean isOnline;

    private LocalDateTime lastActive;

    @JsonIgnore
    private String image_public_id;

    private String nick_name;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createDate;

    @JsonIgnore
    @LastModifiedDate
    private  LocalDateTime updatedDate;
}
