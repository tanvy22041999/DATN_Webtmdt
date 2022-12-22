package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.dtos.clone.UpdateOrderDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.OrderEntity;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    String validateOrderBeforeAdd(RegistryOrderDTO orderDTO);

    OrderEntity addNewOrder(String userId, RegistryOrderDTO orderDTO);

    Page<OrderEntity> getAllOrder(Integer limit, Integer page, String keyword, Integer paid, Integer confirmed, Integer active, Integer status, String paymentMethod, String user, String shippingPhone);

    List<OrderEntity> combinePopulateForOrder(List<OrderEntity> orders);

    OrderEntity getById(String id);

    OrderEntity updateOrder(String id, UpdateOrderDTO updateOrder);
}
