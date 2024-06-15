package com.hasiq.discount_codes_management.controllers;

import com.hasiq.discount_codes_management.entity.ProductEntity;
import com.hasiq.discount_codes_management.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductEntity>> findAll(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProductEntity> create(@RequestBody ProductEntity productEntity){
            return new ResponseEntity<>(productService.save(productEntity), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> update(@PathVariable Long id, @RequestBody ProductEntity productEntity){
            return new ResponseEntity<>(productService.update(productEntity,id), HttpStatus.OK);
    }
}
