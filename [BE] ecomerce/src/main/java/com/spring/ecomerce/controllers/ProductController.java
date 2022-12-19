package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.RegistryProductDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ProductEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.services.ProductService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BaseResponseEntity baseResponse;

    @GetMapping()
    public String getAllBrand(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword) throws SystemException {
        try{
            Page<ProductEntity> results = productService.getAll(limit, page, keyword);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("products", results.getContent());
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping()
    public String addNewProduct(@RequestBody RegistryProductDTO productDTO) throws SystemException {
        try{
            String messageValidate = productService.validateProduct(productDTO);
            if(messageValidate != null){
                baseResponse.failed(404, messageValidate);
                return baseResponse.getResponseBody();
            }

            ProductEntity result = productService.addNewProduct(productDTO);
            if(result == null){
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
            }
            else {
                baseResponse.created();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("product", result);
                return baseResponse.getResponseBody(dataResponse);
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }
}
