package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.RegistryColorDTO;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.services.ColorService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BaseResponseEntity baseResponse;

    @GetMapping("/colors")
    public String getAllColors(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                              @RequestParam(value = "page", defaultValue = "0") Integer page) throws SystemException {
        try{
            Page<ColorEntity> results = colorService.getAll(limit, page);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("colors", results.getContent());
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/colors/{id}")
    public String getById(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            ColorEntity result = colorService.findById(id);

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("category", result);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/colors")
    public String addNewColor(@RequestBody RegistryColorDTO colorDTO) throws SystemException {
        try{
            ColorEntity result = colorService.addNewColor(colorDTO);
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

    @PutMapping("/colors/{id}")
    public String updateColor(@PathVariable(value = "id", required = false) String id,
                              @RequestBody RegistryColorDTO updateDTO) throws SystemException {
        try{
            ColorEntity result = colorService.updateColor(id, updateDTO);
            if(result != null){
                baseResponse.updated();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("color", result);
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

    @DeleteMapping("/colors/{id}")
    public String deleteColor(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            boolean result = colorService.deleteColor(id);
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
