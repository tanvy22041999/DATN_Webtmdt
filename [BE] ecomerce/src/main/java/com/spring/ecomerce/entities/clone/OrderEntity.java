package com.spring.ecomerce.entities.clone;

import com.spring.ecomerce.entities.inner.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "order")
public class OrderEntity extends BasicEntity{
    @Id
    private String id;

    private String user;

    private Double totalPrice;

    private Integer totalQuantity;

    private String shippingPhonenumber;

    private String email;

    private String shippingAddress;

    private String note;

    private boolean paid;

    private String paymentMethod;

    private Integer status;

    private boolean confirmed;

    private boolean active;

    private List<OrderItem> orderList;
}
