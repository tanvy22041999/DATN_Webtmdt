package com.spring.ecomerce.entities.clone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "notification")
public class NotificationEntity extends BasicEntity{
    @Id
    private String id;

    private String name;

    private String user;

    private ImageEntity image;

    private String link;

    private String content;

    private boolean active;

    private int type;
}
