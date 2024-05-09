package com.hasiq.discount_codes_management.Controllers;

import com.hasiq.discount_codes_management.DTO.DiscountDTO;
import com.hasiq.discount_codes_management.Entity.PurchaseEntity;
import com.hasiq.discount_codes_management.Service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/purchase")
    public ResponseEntity<PurchaseEntity> purchase(@RequestBody DiscountDTO discountDTO){
        return purchaseService.purchase(discountDTO.getProductId(), discountDTO.getDiscountCode());
    }
}
