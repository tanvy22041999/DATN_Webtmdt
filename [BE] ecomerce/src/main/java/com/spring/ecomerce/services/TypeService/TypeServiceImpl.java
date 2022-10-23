package com.spring.ecomerce.services.TypeService;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.comstants.SystemConstants;
import com.spring.ecomerce.dtos.TypeDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Type;
import com.spring.ecomerce.repositories.TypeRepository.TypeRepository;
import com.spring.ecomerce.securities.JwtUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private MessageManager messageManager;

    private String logicCheckCreate(TypeDTO type){
        List<Type> typeFound = typeRepository.findTypeByTypeNameNear(type.getTypeName());
        if(typeFound != null && typeFound.size() > 0){
            return messageManager.getMessage("ERR0004",null);
        }
        return "";
    }

    private String logicCheckUpdate(String typeId, TypeDTO type){
        if("".equals(type.getTypeName())){
            return messageManager.getMessage("ERR0005", null);
        }

        List<Type> typeFound = typeRepository.findTypeByTypeNameNear(type.getTypeName());
        if(typeFound != null && typeFound.size() > 0){
            return messageManager.getMessage("ERR0004",null);
        }

        Type typeFoundById = typeRepository.findTypeById(typeId);
        if(typeFoundById == null){
            return  messageManager.getMessage("ERR0006", null);
        }
        return "";
    }

    private String  logicCheckDelete(String typeId){
        Type type = typeRepository.findTypeById(typeId);
        if(type == null){
            return  messageManager.getMessage("ERR0006", null);
        }

        return "";
    }

    @Override
    public ServiceResponse<Type> createFoodType(TypeDTO type) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ServiceResponse<Type> result = new ServiceResponse<>();

        try{
            String error = this.logicCheckCreate(type);

            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }
            Type typeSaved = new Type();
            BeanUtils.copyProperties(type, typeSaved);
            typeSaved.setDelFlg(SystemConstants.DEL_FLG_OFF);
            typeSaved.setUpdateUser(userDetails.getUsername());

            typeRepository.save(typeSaved);
            result.setData(typeSaved);
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Type> updateFoodType(String typeId, TypeDTO type) {
        ServiceResponse<Type> result = new ServiceResponse<>();
        try{
            String error = this.logicCheckUpdate(typeId, type);

            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }

            Type typeUpdateFound = typeRepository.findTypeById(typeId);
            typeUpdateFound.setTypeName(type.getTypeName());
            Type typeUpdated =  typeRepository.save(typeUpdateFound);
            result.setData(typeUpdated);
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Type> deleteFoodType(String typeId) {
        ServiceResponse<Type> result = new ServiceResponse<>();
        try{
            String error = this.logicCheckDelete(typeId);
            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }

            Type type = typeRepository.findTypeById(typeId);
            type.setDelFlg(SystemConstants.DEL_FLG_ON);
            typeRepository.save(type);
            result.setData(type);
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Object> getAllType() {
        ServiceResponse<Object> result = new ServiceResponse<>();
        try{
            List<Type> types = typeRepository.fetchAll();
            result.setData(types);
        }catch (Exception ex) {
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }
}
