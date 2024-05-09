package com.hasiq.discount_codes_management.Controllers;

import com.hasiq.discount_codes_management.DTO.DiscountDTO;
import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/discount")
    public ResponseEntity<Map<String,String>> getDiscount(@RequestBody DiscountDTO discountDTO){
        return discountService.getDiscountPrice(discountDTO.getDiscountCode(),discountDTO.getProductId());
    }
}
