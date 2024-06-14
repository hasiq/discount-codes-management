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
        ProductEntity save = productService.save(productEntity);
        if(save != null) {
            return new ResponseEntity<>(save, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> update(@PathVariable Long id, @RequestBody ProductEntity productEntity){
        ProductEntity update = productService.update(productEntity, id);
        if(update != null) {
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
