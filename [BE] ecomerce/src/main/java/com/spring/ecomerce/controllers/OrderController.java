package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.entities.clone.OrderEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.securities.AccountDetailsService;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.services.OrderService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public String addNewOrder(@RequestBody RegistryOrderDTO orderDTO) throws SystemException {
        try{
            String validateMessage = orderService.validateOrderBeforeAdd(orderDTO);
            if(validateMessage != null){
                baseResponse.failed(400, validateMessage);
            }else{
                JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                OrderEntity orderSaved = orderService.addNewOrder(userDetails.getUserLogin().getId(), orderDTO);
            }
            baseResponse.created();
            Map<String, Object> dataResponse = new HashMap<>();
//            dataResponse.put("ad", result);
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }
}
