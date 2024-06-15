package com.hasiq.discount_codes_management.controllers;

import com.hasiq.discount_codes_management.entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.service.PromoCodeService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/promoCodes")
@RestController
public class PromoCodesController {

    private final PromoCodeService promoCodeService;

    public PromoCodesController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @GetMapping("")
    public ResponseEntity<List<PromoCodeEntity>> findAll(){
        return new ResponseEntity<>(promoCodeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<PromoCodeEntity> findByCode(@PathVariable String code){
            return new ResponseEntity<>(promoCodeService.findByCode(code), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PromoCodeEntity> create(@RequestBody PromoCodeEntity promoCodeEntity) {
            return new ResponseEntity<>(promoCodeService.save(promoCodeEntity), HttpStatus.CREATED);
    }
}
