package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.TokenDetails;
import com.spring.ecomerce.dtos.clone.RegistryNofifyDTO;
import com.spring.ecomerce.entities.clone.NotificationEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.securities.JwtTokenUtils;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.services.NotifyService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BaseResponseEntity baseResponse;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @GetMapping("/notification")
    public String getAllnotify(@RequestParam(value = "limit", defaultValue = "10") Integer limit,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "user", defaultValue = "") String user,
                               @RequestParam(value="admin", defaultValue = "") String admin,
                               @RequestParam(value = "type", defaultValue = "-1") Integer type) throws SystemException {
        try{
            Page<NotificationEntity> results = notifyService.getAllNotify(user, admin, type, limit, page);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("notifications", results.getContent());
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @GetMapping("/notification-newest")
    public String getNewestNotify(@RequestParam(value = "limit", defaultValue = "5") Integer limit,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "user", defaultValue = "") String user,
                               @RequestParam(value="admin", defaultValue = "") String admin,
                               @RequestParam(value = "type", defaultValue = "-1") Integer type) throws SystemException {
        try{
            Page<NotificationEntity> results = notifyService.getAllNotify(user, admin, type, limit, page);

            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("total", results.getTotalElements());
            dataResponse.put("notifications", results.getContent());
            baseResponse.retrieved();
            return baseResponse.getResponseBody(dataResponse);

        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @PostMapping("/notification")
    public String addNewNotify(@RequestBody RegistryNofifyDTO nofifyDTO) throws SystemException {
        try{
            NotificationEntity result = notifyService.addNewNotify(nofifyDTO);
            if(result == null){
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
            }
            else {
                baseResponse.created();
                Map<String, Object> dataResponse = new HashMap<>();
                dataResponse.put("notification", result);
                return baseResponse.getResponseBody(dataResponse);
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_CREATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PutMapping("/notification/{id}")
    public String updateNotify(@PathVariable(value = "id", required = false) String id,
                              @RequestBody RegistryNofifyDTO nofifyDTO) throws SystemException {
        try{
            NotificationEntity result = notifyService.updateNotify(id, nofifyDTO);
            if(result != null){
                baseResponse.updated();
                return baseResponse.getResponseBody();
            }
            else{
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_GET", null));
        }

        return baseResponse.getResponseBody();
    }

    @DeleteMapping("/notification/{id}")
    public String deleteNotify(@PathVariable(value = "id", required = false) String id) throws SystemException {
        try{
            boolean result = notifyService.deleteNotifyById(id);
            if(result){
                baseResponse.deleted();
                return baseResponse.getResponseBody();
            }
            else{
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_DELETE", null));
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_DELETE", null));
        }

        return baseResponse.getResponseBody();
    }

    @PutMapping("/notification-all")
    public String updateNotifyAllForUser() throws SystemException {
        try{
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TokenDetails infoAuthenticate = jwtTokenUtils.getTokenDetails(userDetails, null);
            boolean result = notifyService.updateNotifyForUser(infoAuthenticate.getUserLogin().getId());
            if(result){
                baseResponse.updated();
                return baseResponse.getResponseBody();
            }
            else{
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
        }

        return baseResponse.getResponseBody();
    }

    @DeleteMapping("/notification-all")
    public String deleteNotifyAllForUser() throws SystemException {
        try{
            JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TokenDetails infoAuthenticate = jwtTokenUtils.getTokenDetails(userDetails, null);
            boolean result = notifyService.deleteNotifyForUser(infoAuthenticate.getUserLogin().getId());
            if(result){
                baseResponse.updated();
                return baseResponse.getResponseBody();
            }
            else{
                baseResponse.failed(HttpStatus.SC_BAD_REQUEST, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
            }
        }catch (Exception ex){
            baseResponse.failed(HttpStatus.SC_INTERNAL_SERVER_ERROR, messageManager.getMessage("INTERNAL_ERROR_UPDATE", null));
        }

        return baseResponse.getResponseBody();
    }
}
