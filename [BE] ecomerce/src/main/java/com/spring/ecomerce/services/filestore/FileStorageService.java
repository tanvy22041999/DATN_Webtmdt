package com.spring.ecomerce.services.filestore;

import com.spring.ecomerce.dtos.FileStoreResult;
import org.springframework.core.io.Resource;

import java.io.File;

public interface FileStorageService {
    FileStoreResult storeFile(File file, String subFolder);

    Resource loadFile(String location);
}
