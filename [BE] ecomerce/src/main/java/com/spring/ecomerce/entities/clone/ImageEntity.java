package com.spring.ecomerce.entities.clone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "image")
public class ImageEntity {
    @Id
    private String id;
    private String publicUrl;
}
