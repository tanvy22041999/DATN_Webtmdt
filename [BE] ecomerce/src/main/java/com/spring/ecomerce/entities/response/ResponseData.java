package com.spring.ecomerce.entities.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseData {
    private boolean success;

    private int statusCode = 200;

    private String message;

    private Object data;
}
