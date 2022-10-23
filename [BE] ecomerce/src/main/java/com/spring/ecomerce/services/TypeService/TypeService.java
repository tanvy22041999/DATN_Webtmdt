package com.spring.ecomerce.services.TypeService;

import com.spring.ecomerce.dtos.TypeDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Type;

public interface TypeService {
    ServiceResponse<Type> createFoodType(TypeDTO type);

    ServiceResponse<Type> updateFoodType(String typeId, TypeDTO type);

    ServiceResponse<Type> deleteFoodType(String typeId);

    ServiceResponse<Object> getAllType();
}
