package com.spring.ecomerce.services;

import com.spring.ecomerce.dtos.clone.RegistryProductDTO;
import com.spring.ecomerce.entities.clone.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductEntity> getAll(Integer limit, Integer page, String keyword);

    ProductEntity getDetailProduct(String id);
    String validateProduct(RegistryProductDTO registryProductDTO);
    ProductEntity addNewProduct(RegistryProductDTO registryProductDTO);
    List<ProductEntity> getHotSold();
}
