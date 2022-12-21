package com.spring.ecomerce.dtos;

import com.spring.ecomerce.entities.inner.ProductSelectedDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegistryOrderDTO {
    private String email;

    private Boolean paid;

    private String note;

    private String paymentMethod;

    private String shippingAddress;

    private String shippingPhonenumber;

    private Integer status;

    private Double totalPrice;

    private Integer totalQuantity;

    private List<ProductSelectedDTO> orderList;

    private String errorProcessing;
}
