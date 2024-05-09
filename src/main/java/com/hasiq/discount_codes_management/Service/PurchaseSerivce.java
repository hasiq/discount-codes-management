package com.hasiq.discount_codes_management.Service;

import org.springframework.stereotype.Service;

@Service
public class PurchaseSerivce {

    private final DiscountService discountService;

    public PurchaseSerivce(DiscountService discountService) {
        this.discountService = discountService;
    }

//    public void purchase(Long productId, String code){
//        discountService.getDiscountPrice(code,productId);
//    }
}
