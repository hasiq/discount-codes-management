package com.hasiq.discount_codes_management.service;

import com.hasiq.discount_codes_management.entity.ProductEntity;
import com.hasiq.discount_codes_management.repository.ProductRepository;
import com.hasiq.discount_codes_management.Exceptions.BadRequestException;
import com.hasiq.discount_codes_management.Exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public ProductEntity save(ProductEntity productEntity) {
        if((productEntity.getName() == null || productEntity.getPrice() == null || productEntity.getCurrency() == null) || (productEntity.getPrice() <= 0) || productEntity.getName().isBlank() || productEntity.getCurrency().toString().isBlank())
            throw new BadRequestException("Invalid product data");
        return productRepository.save(productEntity);
    }

    public ProductEntity update(ProductEntity productEntity, Long id) {
        if(productRepository.findById(id).isPresent()) {
            ProductEntity productEntity1 = productRepository.findById(id).get();
            productEntity1.setName(productEntity.getName());
            productEntity1.setPrice(productEntity.getPrice());
            productEntity1.setDescription(productEntity.getDescription());
            productEntity1.setCurrency(productEntity.getCurrency());
            productRepository.save(productEntity1);
            return productEntity1;
        }
        throw new NotFoundException("Product not found");
    }
}
