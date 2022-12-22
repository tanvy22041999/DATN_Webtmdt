package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.dtos.clone.RegistryBrandDTO;
import com.spring.ecomerce.dtos.clone.UpdateOrderDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.OrderEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.securities.AccountDetailsService;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.services.OrderService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BaseResponseEntity baseResponse;

    @Autowired
    private AccountDetailsService accountDetailsService;

    @GetMapping
    public String getAllOrder(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "paid", required = false) Integer paid,
                              @RequestParam(value = "confirmed", required = false) Integer confirmed,
                              @RequestParam(value = "active", required = false) Integer active,
                              @RequestParam(value = "status", required = false) Integer status,
                              @RequestParam(value = "paymentMethod", defaultValue = "") String paymentMethod,
                              @RequestParam(value = "user", required = false) String user,
                              @RequestParam(value = "phone", defaultValue = "") String shippingPhone) throws SystemException {
        try{
            Page<OrderEntity> results = orderService.getAllOrder(limit, page,keyword,paid,confirmed,active,status,paymentMethod,user,shippingPhone);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("limit", limit);
            dataResponse.put("page", page);
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("orders", orderService.combinePopulateForOrder(results.getContent()));
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            OrderEntity result = orderService.getById(id);

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("order", result);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping
    public String addNewOrder(@RequestBody RegistryOrderDTO orderDTO) throws SystemException {
        try{
            String validateMessage = orderService.validateOrderBeforeAdd(orderDTO);
            if(validateMessage != null){
                baseResponse.failed(400, validateMessage);
            }else{
                JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                OrderEntity orderSaved = orderService.addNewOrder(userDetails.getUserLogin().getId(), orderDTO);
                baseResponse.created();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("order", orderSaved);
                return baseResponse.getResponseBody(dataResponse);
            }

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PutMapping("/{id}")
    public String updateBrand(@PathVariable(value = "id", required = false) String id,
                              @RequestBody UpdateOrderDTO updateOrder) throws SystemException {
        try{
            OrderEntity result = orderService.updateOrder(id, updateOrder);
            if(result != null){
                baseResponse.updated();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("data", result);
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
}
