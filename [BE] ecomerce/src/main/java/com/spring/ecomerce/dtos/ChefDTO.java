package com.spring.ecomerce.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChefDTO {
    private String chefName;

    private MultipartFile img;
}
