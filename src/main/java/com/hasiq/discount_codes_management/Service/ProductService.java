package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.Entity.ProductEntity;
import com.hasiq.discount_codes_management.Repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<ProductEntity>> findAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<ProductEntity> save(ProductEntity productEntity) {
        if(productEntity.getPrice() <= 0 || productEntity.getName().isBlank() || productEntity.getCurrency().isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(productRepository.save(productEntity), HttpStatus.CREATED);
    }

    public ResponseEntity<ProductEntity> update(ProductEntity productEntity, Long id) {
        if(productRepository.findById(id).isPresent()) {
            ProductEntity productEntity1 = productRepository.findById(id).get();
            productEntity1.setName(productEntity.getName());
            productEntity1.setPrice(productEntity.getPrice());
            productEntity1.setDescription(productEntity.getDescription());
            productEntity1.setCurrency(productEntity.getCurrency());
            productRepository.save(productEntity1);
            return ResponseEntity.ok(productEntity1);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
