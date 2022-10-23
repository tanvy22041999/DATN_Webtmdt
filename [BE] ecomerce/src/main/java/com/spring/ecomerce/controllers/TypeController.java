package com.spring.ecomerce.controllers;

import com.spring.ecomerce.commons.ResponseUtils;
import com.spring.ecomerce.dtos.TypeDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Type;
import com.spring.ecomerce.entities.response.ResponseData;
import com.spring.ecomerce.services.TypeService.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping("/admin/type/add-new")
    public ResponseEntity<ResponseData> addNewType(@RequestBody() TypeDTO type){
        ServiceResponse<Type> resultSave = typeService.createFoodType(type);

        return ResponseUtils.getResponse(resultSave);
    }

    @PostMapping("/admin/type/update/{id}")
    public  ResponseEntity<ResponseData> updateType(@PathVariable("id") String typeId, @RequestBody() TypeDTO type){
        ServiceResponse<Type> resultUpdate = typeService.updateFoodType(typeId, type);

        return ResponseUtils.getResponse(resultUpdate);
    }

    @PostMapping("/admin/type/delete/{id}")
    public  ResponseEntity<ResponseData> deleteType(@PathVariable("id") String typeId){
        ServiceResponse<Type> resultDelete = typeService.deleteFoodType(typeId);
        return ResponseUtils.getResponse(resultDelete);
    }

    @GetMapping("/type/fetch-all")
    public  ResponseEntity<ResponseData> getAllType(){
        ServiceResponse<Object> resultFetch = typeService.getAllType();
        return  ResponseUtils.getResponse(resultFetch);
    }
}
