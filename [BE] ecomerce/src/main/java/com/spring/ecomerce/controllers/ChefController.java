package com.spring.ecomerce.controllers;

import com.spring.ecomerce.commons.ResponseUtils;
import com.spring.ecomerce.dtos.ChefDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Chef;
import com.spring.ecomerce.entities.response.ResponseData;
import com.spring.ecomerce.services.ChefService.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class ChefController {
    @Autowired
    private ChefService chefService;

    @PostMapping(value = "/admin/chef/add-new")
    public ResponseEntity<ResponseData> addNewType(@ModelAttribute() ChefDTO chef){

        ServiceResponse<Chef> resultSave = chefService.createChef(chef);
        return ResponseUtils.getResponse(resultSave);
    }

    @PostMapping("/admin/chef/update/{id}")
    public  ResponseEntity<ResponseData> updateType(@PathVariable("id") String chefId, @RequestBody() ChefDTO chef){
        ServiceResponse<Chef> resultUpdate = chefService.updateChef(chefId, chef);
        return ResponseUtils.getResponse(resultUpdate);
    }

    @PostMapping("/admin/chef/delete/{id}")
    public  ResponseEntity<ResponseData> deleteType(@PathVariable("id") String chefId){
        ServiceResponse<Chef> resultDelete = chefService.deleteChef(chefId);
        return ResponseUtils.getResponse(resultDelete);
    }

    @GetMapping("/chef/fetch-all")
    public  ResponseEntity<ResponseData> getAllType(){
        ServiceResponse<Object> resultFetch = chefService.getAllChef();
        return  ResponseUtils.getResponse(resultFetch);
    }
}
