package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    
}
