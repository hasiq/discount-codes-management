package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.Entity.ProductEntity;
import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Repository.ProductRepository;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DiscountService {

    private final ProductRepository productRepository;

    private final PromoCodeRepository promoCodeRepository;

    public DiscountService(ProductRepository productRepository, PromoCodeRepository promoCodeRepository) {
        this.productRepository = productRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    public ResponseEntity<Map<String, String>> getDiscountPrice(String code, Long id) {
       if(!(productRepository.existsById(id) && promoCodeRepository.existsByCode(code))) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
           Map<String, String> response = new HashMap<>();
           ProductEntity product = productRepository.findById(id).get();
           PromoCodeEntity discountCode = promoCodeRepository.findByCode(code);

          if(discountCode.getExpirationDate().isBefore(LocalDate.now())) {
              response.put("Price", product.getPrice().toString());
              response.put("Warning", "Discount Code Expired");
              return new ResponseEntity<>(response, HttpStatus.CONFLICT);
          }
          else if(!discountCode.getCurrency().equals(product.getCurrency())) {
              response.put("Price", product.getPrice().toString());
              response.put("Warning", "Discount Code Currency Mismatch");
              return new ResponseEntity<>(response, HttpStatus.CONFLICT);
          }
          else if (discountCode.getLeftUsages() <= 0) {
            response.put("Price", product.getPrice().toString());
            response.put("Warning", "Discount Code has no usages");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
          }
          else if (product.getPrice() - discountCode.getDiscount() < 0 || discountCode.getDiscount() < 0) {
              response.put("Price", String.valueOf(0.0));
              return new ResponseEntity<>(response, HttpStatus.OK);
          }
          if(discountCode.getIsPercent()) {
              response.put("Price", String.valueOf(product.getPrice() - (discountCode.getDiscount() * product.getPrice() / 100)));
              return new ResponseEntity<>(response, HttpStatus.OK);
          }
          else {
              response.put("Price", String.valueOf(product.getPrice() - discountCode.getDiscount()));
              return new ResponseEntity<>(response, HttpStatus.OK);
          }
    }
}
