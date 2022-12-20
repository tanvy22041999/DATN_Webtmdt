package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.entities.clone.OrderEntity;
import com.spring.ecomerce.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String validateOrderBeforeAdd(RegistryOrderDTO orderDTO) {

        return null;
    }

    @Override
    public OrderEntity addNewOrder(String userId, RegistryOrderDTO orderDTO) {
        return null;
    }
}
