package com.spring.ecomerce.dtos.clone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.ecomerce.comstants.SystemConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class    RegistryAdDTO extends UpdateAdDTO{
    private MultipartFile image;
}
