package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @PostConstruct
    public void init() {
        promoCodeRepository.save(new PromoCodeEntity("AAAAxd",1.00,"PLN", LocalDate.now(),20));
    }
}
