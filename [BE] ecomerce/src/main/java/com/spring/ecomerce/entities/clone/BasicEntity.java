package com.spring.ecomerce.entities.clone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.ecomerce.comstants.SystemConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
public abstract class BasicEntity {
    public BasicEntity(){
        validFlg = 1;
        delFlg = 0;
    }
    @JsonIgnore
    private int delFlg;
    @JsonIgnore
    private int validFlg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConstants.DATETIME_FORMAT)
    @CreatedDate
    private Date createTimeStamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SystemConstants.DATETIME_FORMAT)
    @LastModifiedDate
    private Date updateTimeStamp;

    @LastModifiedBy
    private String updateUser;
}
