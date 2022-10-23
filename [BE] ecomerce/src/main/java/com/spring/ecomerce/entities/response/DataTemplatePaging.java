package com.spring.ecomerce.entities.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataTemplatePaging {
    private int page;

    private int size;

    private long total_count;

    private int total_page;

    Object data;
}
