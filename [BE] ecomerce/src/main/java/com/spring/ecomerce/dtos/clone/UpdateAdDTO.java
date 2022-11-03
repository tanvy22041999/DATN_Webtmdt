package com.spring.ecomerce.dtos.clone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.ecomerce.comstants.SystemConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class UpdateAdDTO {
    private String name;
    private String content;
    private String link;
    private boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConstants.DATETIME_FORMAT)
    @DateTimeFormat(pattern = SystemConstants.DATETIME_FORMAT)
    private Date startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConstants.DATETIME_FORMAT)
    @DateTimeFormat(pattern = SystemConstants.DATETIME_FORMAT)
    private Date endedAt;
}
