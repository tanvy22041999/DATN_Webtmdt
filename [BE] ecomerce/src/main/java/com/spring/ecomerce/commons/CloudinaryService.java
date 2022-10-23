package com.spring.ecomerce.commons;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class CloudinaryService {
    private final Cloudinary cloudinary ;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dow0lu50x",
                "api_key", "821191592441416",
                "api_secret", "hStgEbItYDh5WtDUUBSF6qqCa0o",
                "secure", true));
    }

    public Map uploadImageProfile(MultipartFile file){
        try{
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","image_profile"));
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public Map deleteImageProfile(String public_id){
        try{
            return cloudinary.uploader().destroy(public_id, ObjectUtils.asMap("folder","image_profile"));
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public Map uploadImageProduct(MultipartFile file){
        try{
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","image_product"));
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public Map uploadImageBrand(MultipartFile file){
        try{
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","image_brand"));
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    public Map deleteImageProduct(String public_id){
        try{
            return cloudinary.uploader().destroy(public_id, ObjectUtils.asMap("folder","image_product"));
        }
        catch(Exception exception)
        {
            return null;
        }
    }
}
