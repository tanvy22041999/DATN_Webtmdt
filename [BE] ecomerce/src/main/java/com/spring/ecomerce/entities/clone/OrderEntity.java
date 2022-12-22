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
@Document(collection = "order")
public class OrderEntity extends BasicEntity{
    public OrderEntity(){
        status = -1;
    }

    @Id
    private String id;

    private String user;

    private Double totalPrice;

    private Integer totalQuantity;

    private String shippingPhonenumber;

    private String email;

    private String shippingAddress;

    private String note;

    private String paymentMethod;

    private boolean paid;

    private Integer status;

    private boolean confirmed;

    private boolean active;

    private List<OrderItem> orderList;
}
