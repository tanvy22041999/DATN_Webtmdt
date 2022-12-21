package com.spring.ecomerce.services.impl;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.clone.ColorProductDTO;
import com.spring.ecomerce.dtos.clone.RegistryProductDTO;
import com.spring.ecomerce.dtos.clone.SpecifyProductDTO;
import com.spring.ecomerce.entities.clone.*;
import com.spring.ecomerce.entities.inner.ColorProduct;
import com.spring.ecomerce.entities.inner.SpecifyProduct;
import com.spring.ecomerce.repositories.ProductRepository;
import com.spring.ecomerce.services.*;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SelectorService selectorService;

    @Autowired
    private MessageManager messageManager;

    @Override
    public Page<ProductEntity> getAll(Integer limit, Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, limit);

        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        if(!"".equals(keyword)){
            keyword = keyword.replace("/[`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/]/gi", "").trim();
            Map<String, Object> queryName = new HashMap<>();
            queryName.put("$regex", ".*" + keyword + ".*");
            queryName.put("$options", "i");
            queryData.put("name", queryName);
        }

        Page<ProductEntity> results = productRepository.getByConditionsForPageable(queryData, pageable);
        return results;
    }

    @Override
    public ProductEntity getDetailProduct(String id) {
        Optional<ProductEntity> result = productRepository.findById(id);
        if(result.isPresent()){
            ProductEntity productFound = result.get();
            return productFound;
        }
        return null;
    }

    @Override
    public String validateProduct(RegistryProductDTO registryProductDTO) {
        String productName = registryProductDTO.getName();
        String pathSeo = registryProductDTO.getPathseo();
        String productCategoryId = registryProductDTO.getCategory();
        String productBrandId = registryProductDTO.getBrand();
        String productGroupId = registryProductDTO.getGroup();

        if(productName == null || "".equals(productName)){
            return messageManager.getMessage("ERROR_EMPTY_FIELD", new String[]{"Product name"});
        }

        ProductEntity productFindByName = this.findProductByKeyPairAndIgnoredCase("name", productName);
        if(productFindByName != null){
            return messageManager.getMessage("ERROR_STORED", new String[]{"Product name"});
        }

        ProductEntity productFindByPathseo = this.findProductByKeyPairAndIgnoredCase("pathseo", pathSeo);
        if(productFindByPathseo != null){
            return messageManager.getMessage("ERROR_STORED", new String[]{"Product pathseo"});
        }

        if(productCategoryId != null){
            CategoryEntity categoryFoundById = categoryService.findById(productCategoryId);
            if(categoryFoundById == null){
                return messageManager.getMessage("ERROR_ID_IDENTIFY", new String[]{"category"});
            }
        }
        if(productBrandId != null){
            BrandEntity groupFoundById = brandService.findById(productBrandId);
            if(groupFoundById == null){
                return messageManager.getMessage("ERROR_ID_IDENTIFY", new String[]{"brand"});
            }
        }

        if(productGroupId != null){
            GroupEntity groupFoundById = this.groupService.findById(productGroupId);
            if(groupFoundById == null){
                return messageManager.getMessage("ERROR_ID_IDENTIFY", new String[]{"group"});
            }
        }

        return null;
    }

    @Override
    public ProductEntity addNewProduct(RegistryProductDTO registryProductDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(registryProductDTO.getName());
        productEntity.setAmount(registryProductDTO.getAmount());
        productEntity.setPathseo(registryProductDTO.getPathseo());
        productEntity.setWarrently(registryProductDTO.getWarrently());
        productEntity.setCircumstance(registryProductDTO.getCircumstance());
        productEntity.setIncluded(registryProductDTO.getIncluded());
        productEntity.setBigimage(imageService.findById(registryProductDTO.getBigimage()));

        List<ImageEntity> imageSaved = new ArrayList<>();
        List<String> imageIds = registryProductDTO.getImage();
        if(imageIds != null && imageIds.size() > 0){
            for(String imageId : imageIds){
                ImageEntity imageEntity = imageService.findById(imageId);
                if(imageEntity != null){
                    imageSaved.add(imageEntity);
                }
            }
        }
        productEntity.setImage(imageSaved);

        productEntity.setDescription(registryProductDTO.getDescription());
        productEntity.setDescText(registryProductDTO.getDescText());
        productEntity.setWeight(registryProductDTO.getWeight());
        productEntity.setHeight(registryProductDTO.getHeight());
        productEntity.setLength(registryProductDTO.getLength());
        productEntity.setWidth(registryProductDTO.getWidth());
        if(registryProductDTO.getCategory() != null){
            productEntity.setCategory(categoryService.findById(registryProductDTO.getCategory()));
        }
        if(registryProductDTO.getBrand() != null){
            productEntity.setBrand(brandService.findById(registryProductDTO.getBrand()));
        }
        if(productEntity.getGroup() != null){
            productEntity.setGroup(groupService.findById(registryProductDTO.getGroup()));
        }

        //Specification product
        List<SpecifyProductDTO> specificationEntities = registryProductDTO.getSpecifications();
        if(specificationEntities != null && specificationEntities.size() > 0){
            List<SpecifyProduct> specifyProducts = new ArrayList<>();
            for (SpecifyProductDTO specification : specificationEntities){
                if(specification.getId() != null){
                    SpecificationEntity specificationFound = specificationService.findById(specification.getId());
                    if(specificationFound != null){
                        SpecifyProduct specifyProduct = new SpecifyProduct();
                        specifyProduct.setSpecification(specificationFound);
                        specifyProduct.setName(specificationFound.getName());
                        specifyProduct.setSelection(new ArrayList<>());
                        specifyProduct.setValue("");
                        if(specification.getValue() != null && !"".equals(specification.getValue())){
                            String value = specification.getValue();
                            String valueIdStr = value.replace("[","").replace("]","");
                            String[] valueIds = valueIdStr.split(",");
                            if(valueIds.length > 0){
                                List<SelectorEntity> seletorSelected = new ArrayList<>();
                                for(String valueId :  valueIds){
                                    SelectorEntity selectorEntity = selectorService.findById(valueId);
                                    if(selectorEntity != null){
                                        seletorSelected.add(selectorEntity);
                                    }
                                }
                                specifyProduct.setValue(value);
                                specifyProduct.setSelection(seletorSelected);
                            }
                        }
                    }
                }
            }
            productEntity.setSpecifications(specifyProducts);
        }


        //Color
        List<ColorProduct> colorSaved = new ArrayList<>();
        List<ColorProductDTO> colors = registryProductDTO.getColors();
        List<ColorProductDTO> validColorProduct = new ArrayList<>();
        if(colors != null && colors.size() > 0){
         for(ColorProductDTO colorProduct: colors){
             ColorEntity colorEntity = colorService.findById(colorProduct.getId());
             if(colorEntity != null){
                 validColorProduct.add(colorProduct);

                 ColorProduct validColor = new ColorProduct();
                 validColor.setNameEn(colorEntity.getNameVn());
                 validColor.setNameVn(colorEntity.getNameVn());
                 validColor.setAmount(colorProduct.getAmount());
                 validColor.setPrice(colorProduct.getPrice());
                 validColor.setRealPrice(colorProduct.getRealPrice());
                 if(colorProduct.getImage() != null){
                     ImageEntity imageColor = imageService.findById(colorProduct.getImage());
                     if(imageColor != null){
                         validColor.setImage(imageColor);
                         validColor.setImageLink(imageColor.getPublicUrl());
                     }
                     else{
                         validColor.setImageLink(colorProduct.getImageLink());
                     }
                 }
                 colorSaved.add(validColor);
             }
         }
        }

        List<Double> prices = validColorProduct.stream().map(item -> item.getPrice()).collect(Collectors.toList());
        List<Double> realPrices = validColorProduct.stream().map(item -> item.getRealPrice()).collect(Collectors.toList());

        if(prices.size() > 0){
            Double minPrice = Collections.min(prices);
            Double maxPrice = Collections.max(prices);
            productEntity.setPriceMin(minPrice);
            productEntity.setPriceMax(maxPrice);
        }

        if(realPrices.size() > 0){
            Double realMinPrice = Collections.min(prices);
            Double realMaxPrice = Collections.max(prices);
            productEntity.setRealPriceMin(realMinPrice);
            productEntity.setRealPriceMax(realMaxPrice);
        }

        productEntity.setColors(colorSaved);

        return productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> getHotSold() {
        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        queryData.put("$unwind", "$order_list");
        queryData.put("$project", Map.of("order_list", 1, "_id",0));
        queryData.put("$sort", Map.of("count", 1));
        queryData.put("$limit", 4);
        return productRepository.getByConditionsForList(queryData);
    }

    @Override
    public boolean saveAll(List<ProductEntity> productChanged) {
        List<ProductEntity> productSaved = productRepository.saveAll(productChanged);
        if(productSaved == null || productSaved.size() != productChanged.size()) return false;
        return true;
    }

    private ProductEntity findProductByKeyPairAndIgnoredCase(String key, String value){
        BSONObject queryData = new BasicBSONObject();
        queryData.put("validFlg", 1);
        queryData.put("delFlg", 0);
        Map<String, Object> queryName = new HashMap<>();
        queryName.put("$regex", ".*" + value + ".*");
        queryName.put("$options", "i");
        queryData.put("key", queryName);
        return productRepository.findProductByKeyPairAndIgnoredCase(queryData);
    }
}
