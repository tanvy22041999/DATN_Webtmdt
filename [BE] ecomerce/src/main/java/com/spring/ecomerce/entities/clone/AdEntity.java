package com.spring.ecomerce.entities.clone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.ecomerce.comstants.SystemConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "ad")
public class AdEntity extends BasicEntity {
    @Id
    private String id;
    private String name;
    private String content;
    private ImageEntity image;
    private String link;
    private Boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConstants.DATETIME_FORMAT)
    private Date startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConstants.DATETIME_FORMAT)
    private Date endedAt;
}
