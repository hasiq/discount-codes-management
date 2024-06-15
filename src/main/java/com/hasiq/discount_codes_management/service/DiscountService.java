package com.hasiq.discount_codes_management.service;

import com.hasiq.discount_codes_management.entity.ProductEntity;
import com.hasiq.discount_codes_management.entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.repository.ProductRepository;
import com.hasiq.discount_codes_management.repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.Exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountService {

    private final ProductRepository productRepository;

    private final PromoCodeRepository promoCodeRepository;

    public DiscountService(ProductRepository productRepository, PromoCodeRepository promoCodeRepository) {
        this.productRepository = productRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    public Map<String, String> getDiscountPrice(String code, Long id) {

       if(productRepository.findById(id).isEmpty() || promoCodeRepository.findByCode(code) == null) {
            throw new NotFoundException("Product or promo code not found");
           }
            ProductEntity product = productRepository.findById(id).get();
            PromoCodeEntity discountCode = promoCodeRepository.findByCode(code);
           Map<String, String> response = new HashMap<>();

          if(discountCode.getExpirationDate().isBefore(LocalDate.now())) {
              response.put("Price", product.getPrice().toString());
              response.put("Warning", "Discount Code Expired");
              return response;
          }
          else if(!discountCode.getCurrency().equals(product.getCurrency())) {
              response.put("Price", product.getPrice().toString());
              response.put("Warning", "Discount Code Currency Mismatch");
              return response;
          }
          else if (discountCode.getLeftUsages() <= 0) {
            response.put("Price", product.getPrice().toString());
            response.put("Warning", "Discount Code has no usages");
            return response;
          }
        else if(discountCode.getIsPercent()) {
            response.put("Price", String.valueOf(product.getPrice() - (discountCode.getDiscount() * product.getPrice() / 100)));
            return response;
            }
          else if (product.getPrice() - discountCode.getDiscount() < 0 || discountCode.getDiscount() < 0) {
              response.put("Price", String.valueOf(0.0));
              return response;
          }

          else {
              response.put("Price", String.valueOf(product.getPrice() - discountCode.getDiscount()));
              return response;
          }
    }
}
