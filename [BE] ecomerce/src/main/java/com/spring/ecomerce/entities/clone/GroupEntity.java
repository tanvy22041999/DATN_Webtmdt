package com.spring.ecomerce.entities.clone;

import com.spring.ecomerce.entities.inner.GroupProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "group")
public class GroupEntity extends BasicEntity{
    @Id
    private String id;
    private String name;
    private List<GroupProduct> products;
}
