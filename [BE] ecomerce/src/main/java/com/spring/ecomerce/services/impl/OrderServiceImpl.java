package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.RegistryOrderDTO;
import com.spring.ecomerce.dtos.clone.UpdateOrderDTO;
import com.spring.ecomerce.entities.clone.BrandEntity;
import com.spring.ecomerce.entities.clone.ColorEntity;
import com.spring.ecomerce.entities.clone.OrderEntity;
import com.spring.ecomerce.entities.clone.ProductEntity;
import com.spring.ecomerce.entities.inner.ColorProduct;
import com.spring.ecomerce.entities.inner.OrderItem;
import com.spring.ecomerce.entities.inner.ProductSelectedDTO;
import com.spring.ecomerce.repositories.OrderRepository;
import com.spring.ecomerce.services.OrderService;
import com.spring.ecomerce.services.ProductService;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        if (orderDTO.getShippingPhonenumber() == null || "".equals(orderDTO.getShippingPhonenumber())) {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"So dien thoai nhan hang"});
        }

        if (orderDTO.getShippingAddress() == null || "".equals(orderDTO.getShippingAddress())) {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Dia chi nhan hang"});
        }

        if (orderDTO.getTotalPrice() == null) {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Tong gia"});
        }

        if (orderDTO.getTotalQuantity() == null) {
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Tong so luong"});
        }

        if (orderDTO.getOrderList() == null || orderDTO.getOrderList().size() == 0) {
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
        if ("paypal".equals(orderDTO.getPaymentMethod())) {
            orderEntity.setPaid(true);
            orderEntity.setPaymentMethod("paypal");
        } else {
            orderEntity.setPaymentMethod("local");
        }

        List<OrderItem> orderList = new ArrayList<>();
        List<ProductEntity> productEntities = new ArrayList<>();
        List<ProductSelectedDTO> productSelected = orderDTO.getOrderList();
        for (ProductSelectedDTO productSelect : productSelected) {
            OrderItem orderItem = new OrderItem();

            ProductEntity productOrder = productService.getDetailProduct(productSelect.getProduct().getId());
            if (productOrder != null) {
                List<ColorProduct> colorFilter = productOrder.getColors().stream().filter(item -> item.getId().equals(productSelect.getColor())).collect(Collectors.toList());
                if (colorFilter.size() > 0) {
                    ColorProduct colorSelected = colorFilter.get(0);
                    if (colorSelected.getAmount() - productSelect.getQuantity() >= 0) {
                        for (ColorProduct colorChange : productOrder.getColors()) {
                            if (colorChange.getId().equals(colorSelected.getId())) {
                                colorChange.setAmount(colorChange.getAmount() - productSelect.getQuantity());
                                orderItem.setPrice(colorChange.getPrice());
                                orderItem.setNameColor(colorChange.getNameVn());
                            }
                        }
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
            productOrder.setAmount(productOrder.getAmount() - productSelect.getQuantity());
            productEntities.add(productOrder);
            orderItem.setProductId(productOrder.getId());
            orderItem.setImage(productOrder.getBigimage());
            orderItem.setColorId(productSelect.getColor());
            orderItem.setQuantity(productSelect.getQuantity());
            orderList.add(orderItem);
        }

        boolean isModifyProduct = false;
        if (productEntities.size() > 0) {
            isModifyProduct = productService.saveAll(productEntities);
        }
        if (!isModifyProduct) {
            return null;
        }

        orderEntity.setActive(true);
        orderEntity.setConfirmed(false);
        orderEntity.setUser(userId);
        orderEntity.setOrderList(orderList);
        return orderRepository.save(orderEntity);
    }

    @Override
    public Page<OrderEntity> getAllOrder(Integer limit, Integer page, String keyword, Integer paid, Integer confirmed, Integer active, Integer status, String paymentMethod, String user, String shippingPhone) {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);

        if (!"".equals(keyword)) {
            keyword = keyword.replace("/[`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/]/gi", "").trim();
            Map<String, Object> queryName = new HashMap<>();
            queryName.put("$regex", ".*" + keyword + ".*");
            queryName.put("$options", "i");
            queryData.put("name", queryName);
        }

        if (paid != null) {
            queryData.put("paid", paid.equals(1) ? true : false);
        }

        if (confirmed != null) {
            queryData.put("confirmed", confirmed.equals(1) ? true : false);
        }

        if (active != null) {
            queryData.put("active", active.equals(1) ? true : false);
        }

        if (status != null) {
            queryData.put("status", status);
        }

        if (!"".equals(paymentMethod)) {
            queryData.put("paymentMethod", paymentMethod);
        }

        if (!"".equals(user)) {
            queryData.put("user", user);
        }

        if (!"".equals(shippingPhone)) {
            queryData.put("shippingPhonenumber", shippingPhone);
        }

        Page<OrderEntity> orders = orderRepository.getAll(queryData, pageable);
        return orders;
    }


    @Override
    public List<OrderEntity> combinePopulateForOrder(List<OrderEntity> orders) {
        if (orders.size() > 0) {
            for (OrderEntity order : orders) {
                for (OrderItem orderItem : order.getOrderList()) {
                    String product = orderItem.getProductId();
                    orderItem.setProduct(productService.getDetailProduct(product));
                }
            }
            return orders;
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public OrderEntity getById(String id) {
        Optional<OrderEntity> result = orderRepository.findById(id);
        if (result.isPresent()) {
            OrderEntity orderFound = this.combinePopulateForOrder(List.of(result.get())).get(0);
            return orderFound;
        }
        return null;
    }

    @Override
    @Transactional
    public OrderEntity updateOrder(String id, UpdateOrderDTO updateOrder) {
        OrderEntity orderFound = this.getById(id);
        if (orderFound != null) {
            if (updateOrder.getStatus() != null && updateOrder.equals(1)) {
                orderFound.setPaid(true);
            } else {
                if (updateOrder.getActive() != null && updateOrder.getActive().equals(false) && orderFound.getStatus().equals(1)) {
                    List<ProductEntity> productEntities = new ArrayList<>();
                    for (OrderItem orderItem : orderFound.getOrderList()) {
                        ProductEntity productOrder = productService.getDetailProduct(orderItem.getProductId());
                        if (productOrder != null) {
                            for(ColorProduct color : productOrder.getColors()){
                                if(color.getId().equals(orderItem.getColorId())){
                                    productOrder.setAmount(productOrder.getAmount() + orderItem.getQuantity());
                                }
                            }
                            productEntities.add(productOrder);
                        } else {
                            return null;
                        }
                    }

                    productService.saveAll(productEntities);
                }

                if(updateOrder.getStatus() != null){
                    orderFound.setStatus(updateOrder.getStatus());
                }
                if(updateOrder.getActive() != null){
                    orderFound.setActive(updateOrder.getActive());
                }
                if(updateOrder.getConfirmed() != null){
                    orderFound.setConfirmed(updateOrder.getConfirmed());
                }
            }

            return orderRepository.save(orderFound);
        }
        return null;
    }
}
