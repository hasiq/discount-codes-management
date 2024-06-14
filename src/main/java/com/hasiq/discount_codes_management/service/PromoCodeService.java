package com.hasiq.discount_codes_management.service;

import com.hasiq.discount_codes_management.entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.repository.PromoCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    public List<PromoCodeEntity> findAll() {
        return promoCodeRepository.findAll();
    }

    public PromoCodeEntity findByCode(String code) {
        PromoCodeEntity promoCodeEntity = promoCodeRepository.findByCode(code);
        if(promoCodeEntity != null) {
            return promoCodeRepository.findByCode(code);
            }
        else
            return null;
    }

    public PromoCodeEntity save(PromoCodeEntity promoCodeEntity) {
        if(promoCodeEntity.getLeftUsages() > promoCodeEntity.getMaxUsages() || promoCodeEntity.getExpirationDate() == null || promoCodeEntity.getCurrency() == null || promoCodeEntity.getDiscount() == null || promoCodeEntity.getCode() == null || promoCodeEntity.getCode().length() < 3 || promoCodeEntity.getCode().length() > 24 || promoCodeRepository.existsByCode(promoCodeEntity.getCode()))
            return null;
        for(int i = 0; i < promoCodeEntity.getCode().length(); i++){
            if(!Character.isLetterOrDigit(promoCodeEntity.getCode().charAt(i)))
                return null;
        }
        if(promoCodeEntity.getIsPercent() && promoCodeEntity.getDiscount() > 100) {
            return null;
        }
        promoCodeEntity.setLeftUsages(promoCodeEntity.getMaxUsages());
        return  promoCodeRepository.save(promoCodeEntity);
    }


}
