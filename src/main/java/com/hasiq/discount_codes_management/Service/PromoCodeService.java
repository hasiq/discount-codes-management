package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    public ResponseEntity<List<PromoCodeEntity>> findAll() {
        return ResponseEntity.ok(promoCodeRepository.findAll());
    }

    public ResponseEntity<PromoCodeEntity> findByCode(String code) {
        if(promoCodeRepository.existsByCode(code))
            return ResponseEntity.ok(promoCodeRepository.findByCode(code));
        else
            return ResponseEntity.notFound().build();
    }

    public ResponseEntity<PromoCodeEntity> save(PromoCodeEntity promoCodeEntity) {
        if(promoCodeEntity.getExpirationDate() == null || promoCodeEntity.getCurrency() == null || promoCodeEntity.getDiscount() == null || promoCodeEntity.getCode() == null || promoCodeEntity.getCode().length() < 3 || promoCodeEntity.getCode().length() > 24 || promoCodeRepository.existsByCode(promoCodeEntity.getCode()))
            return ResponseEntity.badRequest().build();
        for(int i = 0; i < promoCodeEntity.getCode().length(); i++){
            if(!Character.isLetterOrDigit(promoCodeEntity.getCode().charAt(i)))
                return ResponseEntity.badRequest().build();
        }
        promoCodeEntity.setLeftUsages(promoCodeEntity.getMaxUsages());
        return new ResponseEntity<>(promoCodeRepository.save(promoCodeEntity), HttpStatus.CREATED);
    }

    @PostConstruct
    public void init() {
        promoCodeRepository.save(new PromoCodeEntity("AAAAxd",1.00,"PLN", LocalDate.now(),20, false));
    }
}
