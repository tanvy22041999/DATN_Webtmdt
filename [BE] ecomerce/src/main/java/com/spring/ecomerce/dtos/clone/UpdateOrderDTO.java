package com.spring.ecomerce.dtos.clone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderDTO {
    private Integer status;
    private Boolean active;
    private Boolean confirmed;
}
