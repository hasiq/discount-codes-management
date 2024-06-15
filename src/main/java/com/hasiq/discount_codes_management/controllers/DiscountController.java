package com.hasiq.discount_codes_management.controllers;

import com.hasiq.discount_codes_management.dto.DiscountDTO;
import com.hasiq.discount_codes_management.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/discount")
    public ResponseEntity<Map<String,String>> getDiscount(@RequestBody DiscountDTO discountDTO){
        Map<String, String> discountPrice = discountService.getDiscountPrice(discountDTO.getDiscountCode(), discountDTO.getProductId());
        if(discountPrice.containsKey("Warning")) {
            return new ResponseEntity<>(discountPrice, HttpStatus.CONFLICT);
        }
        else
            return new ResponseEntity<>(discountPrice, HttpStatus.OK);
    }
}
