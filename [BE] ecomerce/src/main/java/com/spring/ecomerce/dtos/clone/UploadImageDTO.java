package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadImageDTO {
    private MultipartFile image;
}
