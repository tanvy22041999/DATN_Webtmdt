package com.spring.ecomerce.arch;

import com.spring.ecomerce.arch.service.ObjectConverter;
import com.spring.ecomerce.exception.SystemException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class BaseResponseEntity {
    @Autowired
    private ObjectConverter objectConverter;

    private boolean success;
    private int code;
    private String message;

    private Map<String, Object> results;

    public void retrieved(){
        success = true;
        code = 200;
        message = "";
        results = new HashMap<>();
    }

    public void created(){
        success = true;
        code = 201;
        message = "";
        results = new HashMap<>();
    }

    public void updated(){
        success = true;
        code = 200;
        message = "";
        results = new HashMap<>();
    }

    public void deleted(){
        success = true;
        code = 200;
        message = "";
        results = new HashMap<>();
    }

    public  void failed(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getResponseBody() throws SystemException {
        results = new HashMap<>();
        results.put("success", success);
        results.put("code", code);
        results.put("message", message);
        return objectConverter.toJsonStringFrom(results);
    }

    public String getResponseBody(Map<String, Object> result) throws SystemException {
        if(results == null){
            results = new HashMap<>();
        }

        results.put("success", success);
        results.put("code", code);
        results.put("message", message);
        results.putAll(result);
        return objectConverter.toJsonStringFrom(results);
    }
}
