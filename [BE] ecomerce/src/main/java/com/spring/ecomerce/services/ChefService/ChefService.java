package com.spring.ecomerce.services.ChefService;

import com.spring.ecomerce.dtos.ChefDTO;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Chef;

public interface ChefService {
    ServiceResponse<Chef> createChef(ChefDTO chef);

    ServiceResponse<Chef> updateChef(String chefId, ChefDTO chef);

    ServiceResponse<Chef> deleteChef(String chefId);

    ServiceResponse<Object> getAllChef();
}
