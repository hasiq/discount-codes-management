package com.hasiq.discount_codes_management.Controllers;

import com.hasiq.discount_codes_management.Entity.ProductEntity;
import com.hasiq.discount_codes_management.Service.ProductService;
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
        return productService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<ProductEntity> create(@RequestBody ProductEntity productEntity){
        return productService.save(productEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> update(@PathVariable Long id, @RequestBody ProductEntity productEntity){
        return productService.update(productEntity,id);
    }
}
