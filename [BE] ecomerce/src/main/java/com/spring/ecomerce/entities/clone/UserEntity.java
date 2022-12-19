package com.spring.ecomerce.entities.clone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "user")
public class UserEntity extends BasicEntity {
    @Id
    private String id;

    private String firstname;
    private String lastname;
    private String phonenumber;
    private String address;
    private ImageEntity image;
    @Email
    private String email;
    private String password;
    private String authType;
    private String confirmed;
    private String role;
    private String token;
    private List<String> history;
}
