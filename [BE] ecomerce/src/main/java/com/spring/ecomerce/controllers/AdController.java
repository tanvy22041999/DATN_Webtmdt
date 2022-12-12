package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.RegistryAdDTO;
import com.spring.ecomerce.entities.clone.AdEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.services.AdService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/rest")
public class AdController {

    @Autowired
    private AdService adService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BaseResponseEntity baseResponse;

    @GetMapping("/ad")
    public String getAllAd(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                           @RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "active", defaultValue = "0") Integer active,
                           @RequestParam(value = "status", required = false) Integer status) throws SystemException {
        try{
            Page<AdEntity> results = adService.getAll(limit, page, active, status);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("ads", results.getContent());
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/ad/{id}")
    public String getById(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            AdEntity result = adService.findById(id);

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("ad", result);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/ad")
    public String addNewAd(@ModelAttribute RegistryAdDTO adDTO) throws SystemException {
        try{
            AdEntity result = adService.addNewAd(adDTO, adDTO.getImage());
            if(result == null){
                throw new SystemException();
            }

            baseResponse.created();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("ad", result);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PutMapping("/ad/{id}")
    public String updateAd(@PathVariable(value = "id", required = false) String id,
                           @ModelAttribute RegistryAdDTO updateDTO) throws SystemException {
        try{
            AdEntity result = adService.updateAd(id, updateDTO);
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

    @DeleteMapping("/ad/{id}")
    public String deleteAd(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            boolean result = adService.deleteAd(id);
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
