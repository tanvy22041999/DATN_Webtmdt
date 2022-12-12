package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.services.BrandService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BaseResponseEntity baseResponse;

    @GetMapping("/brands")
    public String getAllBrand(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "keyword", defaultValue = "") String keyword) throws SystemException {
        try{
            Page<BrandEntity> results = brandService.getAll(limit, page, keyword);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("brands", results.getContent());
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/brands/{id}")
    public String getById(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            BrandEntity result = brandService.findById(id);

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("ad", result);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/brands")
    public String addNewBrand(@ModelAttribute RegistryBrandDTO brandDTO) throws SystemException {
        try{
            BrandEntity result = brandService.addNewBrand(brandDTO);
            if(result == null){
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
            }
            else {
                baseResponse.created();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("brand", result);
                return baseResponse.getResponseBody(dataResponse);
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PutMapping("/brands/{id}")
    public String updateBrand(@PathVariable(value = "id", required = false) String id,
                           @ModelAttribute RegistryBrandDTO updateDTO) throws SystemException {
        try{
            BrandEntity result = brandService.updateBrand(id, updateDTO);
            if(result != null){
                baseResponse.updated();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("ad", result);
                return baseResponse.getResponseBody(dataResponse);
            }
            else{
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @DeleteMapping("/brands/{id}")
    public String deleteBrand(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            boolean result = brandService.deleteBrand(id);
            if(result){
                baseResponse.deleted();
                return baseResponse.getResponseBody();
            }
            else{
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_DELETE", null));
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_DELETE", null));
        }

        return baseResponse.getResponseBody();
    }
}
