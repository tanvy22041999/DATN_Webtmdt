package com.spring.ecomerce.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "types")
public class Type {
    @Id
    private String typeId;

    private boolean delFlg;

    private String typeName;

    @CreatedDate
    private DateTime createDate;

    @LastModifiedDate
    private DateTime updateDate;

    @LastModifiedBy
    private String updateUser;
}
