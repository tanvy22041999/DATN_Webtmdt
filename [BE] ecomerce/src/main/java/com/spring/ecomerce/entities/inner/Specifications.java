package com.spring.ecomerce.entities.inner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Specifications {
    private List<String> selections;
    private String id;
    private String name;
}
