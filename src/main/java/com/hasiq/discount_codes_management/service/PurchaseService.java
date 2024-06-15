package com.hasiq.discount_codes_management.service;

import com.hasiq.discount_codes_management.dto.SalesReportDTO;
import com.hasiq.discount_codes_management.entity.ProductEntity;
import com.hasiq.discount_codes_management.entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.entity.PurchaseEntity;
import com.hasiq.discount_codes_management.repository.ProductRepository;
import com.hasiq.discount_codes_management.repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.repository.PurchaseRepository;
import com.hasiq.discount_codes_management.tools.CurrencyEnum;
import com.hasiq.discount_codes_management.Exceptions.NotFoundException;
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
    private final ProductService productService;
    private final PromoCodeService promoCodeService;


    public PurchaseService(DiscountService discountService, PurchaseRepository purchaseRepository, ProductRepository productRepository, PromoCodeRepository promoCodeRepository, JdbcTemplate jdbcTemplate, ProductService productService, PromoCodeService promoCodeService) {
        this.discountService = discountService;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.promoCodeRepository = promoCodeRepository;
        this.productService = productService;
        this.promoCodeService = promoCodeService;
    }

    public Object purchase(Long productId, String code){
        Map<String, String> map =  discountService.getDiscountPrice(code, productId);
        PromoCodeEntity promoCode = promoCodeService.findByCode(code);
        if(map == null || map.isEmpty())
            throw new NotFoundException("Discount not found");
        if(map.containsKey("Warning"))
            return map;
        ProductEntity product = productRepository.findById(productId).get();
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setRegularPrice(product.getPrice());
        purchase.setCurrency(product.getCurrency());
        purchase.setProductEntity(product);
        if(!promoCode.getIsPercent()) {
           purchase.setDiscountPrice(promoCode.getDiscount());
        }
        else{
            purchase.setDiscountPrice(product.getPrice() * promoCode.getDiscount() / 100);
        }
        purchaseRepository.save(purchase);
        promoCode.setLeftUsages(promoCode.getLeftUsages() - 1);
        promoCodeRepository.save(promoCode);
        return purchase;
    }

    public List<SalesReportDTO> salesReport(){
        List<Object[]> rows = purchaseRepository.salesReport();
        return mapToDTO(rows);
    }

    private List<SalesReportDTO> mapToDTO(List<Object[]> rows){
        List<SalesReportDTO> dtos = new ArrayList<>();
        for(Object[] row : rows){
            SalesReportDTO dto = new SalesReportDTO();
            dto.setCurrency(CurrencyEnum.getById(Byte.parseByte(row[0].toString())));
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
