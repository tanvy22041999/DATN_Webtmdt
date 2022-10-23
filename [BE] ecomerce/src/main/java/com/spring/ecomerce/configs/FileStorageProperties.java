package com.spring.ecomerce.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class FileStorageProperties {

    @Value("${file.download_dir}")
    private String downloadDir;


    @Value("${file.upload_dir}")
    private String uploadDir;
}

