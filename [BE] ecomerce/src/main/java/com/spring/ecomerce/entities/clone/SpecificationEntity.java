package com.spring.ecomerce.entities.clone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "specification")
public class SpecificationEntity extends BasicEntity  {
    @Id
    private String id;

    private String name;

    private List<SelectorEntity> selections;
}
