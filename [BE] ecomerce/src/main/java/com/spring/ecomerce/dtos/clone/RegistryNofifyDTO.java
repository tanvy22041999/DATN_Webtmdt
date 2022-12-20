package com.spring.ecomerce.dtos.clone;

import com.spring.ecomerce.entities.clone.ImageEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryNofifyDTO {
    private String name;

    private String user;

    private String image;

    private String link;

    private String content;

    private boolean active;

    private int type;
}
