package com.hasiq.discount_codes_management.controllers;

import com.hasiq.discount_codes_management.dto.DiscountDTO;
import com.hasiq.discount_codes_management.dto.SalesReportDTO;
import com.hasiq.discount_codes_management.entity.PurchaseEntity;
import com.hasiq.discount_codes_management.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody DiscountDTO discountDTO){
        Object purchase = purchaseService.purchase(discountDTO.getProductId(), discountDTO.getDiscountCode());
        if(purchase == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if(purchase instanceof PurchaseEntity){
            return new ResponseEntity<>(purchase, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(purchase, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/report")
    public ResponseEntity<List<SalesReportDTO>> salesReport(){
        return new ResponseEntity<>(purchaseService.salesReport(), HttpStatus.OK);
    }
}
