package com.spring.ecomerce.services.filestore;

import com.spring.ecomerce.configs.FileStorageProperties;
import com.spring.ecomerce.exceptions.FileStorageException;
import com.spring.ecomerce.exceptions.NotFoundException;
import com.spring.ecomerce.exceptions.ServerException;
import com.spring.ecomerce.dtos.FileStoreResult;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService{
    private final Path fileDownloadDirStorageLocation;

    private final Path fileUploadDirStorageLocation;

    @Value("${file.upload_dir}")
    private String path;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        try {
            this.fileDownloadDirStorageLocation = Paths.get(fileStorageProperties.getDownloadDir()).toAbsolutePath().normalize();
            if (Files.notExists(fileDownloadDirStorageLocation)) {
                File file = fileDownloadDirStorageLocation.toFile();
                file.mkdir();
            }
        } catch (Exception ex) {
            throw new ServerException("Couldn't init downloads directory");
        }

        try {
            this.fileUploadDirStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            if (Files.notExists(fileUploadDirStorageLocation)) {
                File file = fileUploadDirStorageLocation.toFile();
                file.mkdir();
            }
        } catch (Exception ex) {
            throw new ServerException("Couldn't init uploads directory");
        }
    }

    @Override
    public FileStoreResult storeFile(File file, String subFolder) {
        // Normalize file name
        String fileName = generateFileName();
        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        Path targetLocation;
        if (!ObjectUtils.isEmpty(subFolder)) {
            if (!Files.exists(Paths.get(this.fileUploadDirStorageLocation.getFileName().toString() + File.separatorChar + subFolder))) {
                String fullDirectoryPath = this.fileUploadDirStorageLocation.toAbsolutePath().toString() + this.fileUploadDirStorageLocation.getFileName().toString() + File.separator + subFolder;
                File directory = new File(fullDirectoryPath);
                directory.mkdir();
            }
            targetLocation = Paths.get(this.fileUploadDirStorageLocation.getFileName().toString() + File.separatorChar +
                    subFolder + File.separatorChar + fileName);
        } else {
            targetLocation = Paths.get(this.fileUploadDirStorageLocation.getFileName().toString() + File.separatorChar + fileName);
        }
//            FileUtils.copyFile(file, targetLocation.toFile(), true);
//            // xoa file temp
//            FileUtils.deleteQuietly(file);
        FileStoreResult fileStoreResult = new FileStoreResult();
        fileStoreResult.setTenFile(fileName);
        fileStoreResult.setDuongDan(targetLocation.toString());
        return fileStoreResult;
    }

    @Override
    public Resource loadFile(String location) {
        try {
            if (Files.notExists(Paths.get(location))) {
                throw new NotFoundException("Không tìm thấy file");
            }
            Path filePath = Paths.get(location);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("Không tìm thấy file");
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException("Không tìm thấy file ", ex);
        }
    }

    private String generateFileName() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 20; i++) {
            result += (random.nextInt(26) + 'a');
        }
        result += (new SimpleDateFormat("ddmmyyyyHHmmss").format(new Date()));
        return result;
    }
}
