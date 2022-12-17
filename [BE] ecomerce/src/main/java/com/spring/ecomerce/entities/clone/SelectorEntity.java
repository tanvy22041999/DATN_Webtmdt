package com.spring.ecomerce.entities.clone;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "selecttor")
public class SelectorEntity extends BasicEntity{
    @Id
    private String id;
    private String name;
}
