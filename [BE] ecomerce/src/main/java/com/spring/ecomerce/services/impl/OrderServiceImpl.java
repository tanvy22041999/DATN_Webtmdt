package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.entities.clone.OrderEntity;
import com.spring.ecomerce.entities.clone.ProductEntity;
import com.spring.ecomerce.entities.inner.ColorProduct;
import com.spring.ecomerce.entities.inner.OrderItem;
import com.spring.ecomerce.entities.inner.ProductSelectedDTO;
import com.spring.ecomerce.repositories.OrderRepository;
import com.spring.ecomerce.repositories.ProductRepository;
import com.spring.ecomerce.services.OrderService;
import com.spring.ecomerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Override
    public String validateOrderBeforeAdd(RegistryOrderDTO orderDTO) {
        if(orderDTO.getShippingPhonenumber() == null || "".equals(orderDTO.getShippingPhonenumber())){
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"So dien thoai nhan hang"});
        }

        if(orderDTO.getShippingAddress() == null || "".equals(orderDTO.getShippingAddress())){
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Dia chi nhan hang"});
        }

        if(orderDTO.getTotalPrice() == null)
        {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Tong gia"});
        }

        if(orderDTO.getTotalQuantity() == null)
        {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Tong so luong"});
        }

        if(orderDTO.getOrderList() == null || orderDTO.getOrderList().size() == 0){
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Danh sach san pham dat hang"});
        }

        return null;
    }

    @Transactional
    @Override
    public OrderEntity addNewOrder(String userId, RegistryOrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setShippingPhonenumber(orderDTO.getShippingPhonenumber());
        orderEntity.setShippingAddress(orderDTO.getShippingAddress());
        orderEntity.setNote(orderDTO.getNote());
        orderEntity.setEmail(orderDTO.getEmail());
        orderEntity.setTotalPrice(orderDTO.getTotalPrice());
        orderEntity.setTotalQuantity(orderDTO.getTotalQuantity());
        if("paypal".equals(orderDTO.getPaymentMethod())){
            orderEntity.setPaid(true);
            orderEntity.setPaymentMethod("paypal");
            orderEntity.setConfirmed(true);
        }
        orderEntity.setUser(userId);

        List<OrderItem> orderList = new ArrayList<>();
        List<ProductEntity> productEntities = new ArrayList<>();
        List<ProductSelectedDTO> productSelected = orderDTO.getOrderList();
        for(ProductSelectedDTO productSelect : productSelected){
            OrderItem orderItem = new OrderItem();

            ProductEntity productOrder = productService.getDetailProduct(productSelect.getProduct().getId());
            if(productOrder != null){
                List<ColorProduct> colorFilter = productOrder.getColors().stream().filter(item -> item.getId().equals(productSelect.getColor())).collect(Collectors.toList());
                if(colorFilter.size() > 0){
                    ColorProduct colorSelected = colorFilter.get(0);
                    if(colorSelected.getAmount() - productSelect.getQuantity() >= 0){
                        for(ColorProduct colorChange : productOrder.getColors()){
                            if(colorChange.getId().equals(colorSelected.getId())){
                                colorChange.setAmount(colorChange.getAmount() - productSelect.getQuantity());
                                orderItem.setPrice(colorChange.getPrice());
                                orderItem.setNameColor(colorChange.getNameVn());
                            }
                        }
                    }else{
                        return null;
                    }
                }
            }else{
                return null;
            }
            productOrder.setAmount(productOrder.getAmount() - productSelect.getQuantity());
            productEntities.add(productOrder);
            orderItem.setProduct(productOrder.getId());
            orderItem.setImage(productOrder.getBigimage());
            orderItem.setColor(productSelect.getColor());
            orderItem.setQuantity(productSelect.getQuantity());
            orderList.add(orderItem);
        }

        boolean isModifyProduct = false;
        if(productEntities.size() > 0){
            isModifyProduct = productService.saveAll(productEntities);
        }
        if(!isModifyProduct){
            return null;
        }

        orderEntity.setConfirmed(true);
        orderEntity.setOrderList(orderList);
        return orderRepository.save(orderEntity);
    }
}
