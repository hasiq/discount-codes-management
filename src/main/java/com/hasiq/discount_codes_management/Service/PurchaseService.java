package com.hasiq.discount_codes_management.Service;

import com.hasiq.discount_codes_management.DTO.SalesReportDTO;
import com.hasiq.discount_codes_management.Entity.ProductEntity;
import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Entity.PurchaseEntity;
import com.hasiq.discount_codes_management.Repository.ProductRepository;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.Repository.PurchaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseService {

    private final DiscountService discountService;

    private final PurchaseRepository purchaseRepository;

    private final ProductRepository productRepository;

    private final PromoCodeRepository promoCodeRepository;


    public PurchaseService(DiscountService discountService, PurchaseRepository purchaseRepository, ProductRepository productRepository, PromoCodeRepository promoCodeRepository, JdbcTemplate jdbcTemplate) {
        this.discountService = discountService;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.promoCodeRepository = promoCodeRepository;
    }

    public ResponseEntity<PurchaseEntity> purchase(Long productId, String code){
        Map<String, String> map =  discountService.getDiscountPrice(code, productId).getBody();
        if(map == null || map.isEmpty())
            return ResponseEntity.notFound().build();
        if(map.containsKey("Warning"))
            return ResponseEntity.badRequest().body(null);
        ProductEntity product = productRepository.findById(productId).get();
        PurchaseEntity purchase = new PurchaseEntity(LocalDate.now(), product.getPrice(), Double.valueOf(map.get("Price")) + product.getPrice(),product.getCurrency(),product);
        purchaseRepository.save(purchase);
        PromoCodeEntity promoCode = promoCodeRepository.findByCode(code);
        promoCode.setLeftUsages(promoCode.getLeftUsages() - 1);
        promoCodeRepository.save(promoCode);
        return ResponseEntity.ok(purchase);
    }

    public ResponseEntity<List<SalesReportDTO>> salesReport(){
        List<Object[]> rows = purchaseRepository.salesReport();
        return ResponseEntity.ok(mapToDTO(rows));
    }

    private List<SalesReportDTO> mapToDTO(List<Object[]> rows){
        List<SalesReportDTO> dtos = new ArrayList<>();
        for(Object[] row : rows){
            SalesReportDTO dto = new SalesReportDTO();
            dto.setCurrency((String)row[0]);
            dto.setTotalDiscount(convertToDouble(row[1]));
            dto.setTotalAmount((convertToDouble(row[2])));
            dto.setNumberOfPurchases(Math.toIntExact((Long) row[3]));
            dtos.add(dto);
        }
        return dtos;
    }

    private Double convertToDouble(Object value){
        if(value instanceof BigDecimal)
            return ((BigDecimal)value).doubleValue();
        else
            throw new IllegalArgumentException("Unsupported value type");
    }
}
