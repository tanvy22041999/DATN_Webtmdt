package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.entities.clone.OrderEntity;

public interface OrderService {

    String validateOrderBeforeAdd(RegistryOrderDTO orderDTO);
    OrderEntity addNewOrder(String userId, RegistryOrderDTO orderDTO);
}
