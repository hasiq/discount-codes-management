package com.hasiq.discount_codes_management.Controllers;

import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Service.PromoCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PromoCodesController {

    private final PromoCodeService promoCodeService;

    public PromoCodesController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PromoCodeEntity>> findAll(){
        return promoCodeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeEntity> findById(@PathVariable String id){
        return promoCodeService.findByCode(id);
    }

    @PostMapping("/create")
    public ResponseEntity<PromoCodeEntity> create(@RequestBody PromoCodeEntity promoCodeEntity){
        return promoCodeService.save(promoCodeEntity);
    }
}