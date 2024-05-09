package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.Entity.ProductEntity;
import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Entity.PurchaseEntity;
import com.hasiq.discount_codes_management.Repository.ProductRepository;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.Repository.PurchaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PurchaseService {

    private final DiscountService discountService;

    private final PurchaseRepository purchaseRepository;

    private final ProductRepository productRepository;

    private final PromoCodeRepository promoCodeRepository;

    public PurchaseService(DiscountService discountService, PurchaseRepository purchaseRepository, ProductRepository productRepository, PromoCodeRepository promoCodeRepository) {
        this.discountService = discountService;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    public ResponseEntity<PurchaseEntity> purchase(Long productId, String code){
        Map<String, String> map =  discountService.getDiscountPrice(code, productId).getBody();
        if(map.containsKey("Warning"))
            return ResponseEntity.badRequest().body(null);
        ProductEntity product = productRepository.findById(productId).get();
        PurchaseEntity purchase = new PurchaseEntity(LocalDate.now(), product.getPrice(), Double.valueOf(map.get("Price")),product.getCurrency(),product);
        purchaseRepository.save(purchase);
        PromoCodeEntity promoCode = promoCodeRepository.findByCode(code);
        promoCode.setLeftUsages(promoCode.getLeftUsages() - 1);
        promoCodeRepository.save(promoCode);
        return ResponseEntity.ok(purchase);
    }
}
