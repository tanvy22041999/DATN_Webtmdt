package com.spring.ecomerce.arch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.ecomerce.comstants.SystemConstants;
import com.spring.ecomerce.exception.SystemException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Map;

@Component
public class ObjectConverter {
    public String toJsonStringFrom(Object object) throws SystemException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat(SystemConstants.DATETIME_FORMAT));
            String jsonStr = mapper.writeValueAsString(object);
            return jsonStr;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            throw new SystemException("Fail to convert data",ex);
        }
    }

    public String toJsonStringFromMap(Map<String, Object> objMap) throws SystemException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat(SystemConstants.DATETIME_FORMAT));
            String jsonStr = mapper.writeValueAsString(objMap);

            return jsonStr;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            throw new SystemException("Fail to convert data",ex);
        }
    }

    public <T> T toObjectFromJsonString(String jsonStr, Class<T> valueType) throws SystemException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T) mapper.readValue(jsonStr, valueType);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            throw new SystemException("Fail to convert data",ex);
        }
    }
}
