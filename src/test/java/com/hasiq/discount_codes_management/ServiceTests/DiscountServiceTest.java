package com.hasiq.discount_codes_management.ServiceTests;

import com.hasiq.discount_codes_management.Entity.ProductEntity;
import com.hasiq.discount_codes_management.Entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.Repository.ProductRepository;
import com.hasiq.discount_codes_management.Repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.Service.DiscountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class DiscountServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @InjectMocks
    private DiscountService discountService;


    @Test
    void shouldReturnDiscountedValue() {
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity();
        promoCodeEntity.setCode("AAAA3");
        promoCodeEntity.setDiscount(1.0);
        promoCodeEntity.setLeftUsages(20);
        promoCodeEntity.setCurrency("PLN");
        promoCodeEntity.setExpirationDate(LocalDate.of(2024,06,11));
        promoCodeEntity.setMaxUsages(20);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Banana");
        productEntity.setPrice(3.0);
        productEntity.setDescription("test");
        productEntity.setCurrency("PLN");


        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        when(productRepository.existsById(1L)).thenReturn(true);
        when(promoCodeRepository.existsByCode("AAAA3")).thenReturn(true);
        when(promoCodeRepository.existsByCode("AAAA3")).thenReturn(true);
        when(promoCodeRepository.findByCode("AAAA3")).thenReturn(promoCodeEntity);


        ResponseEntity<Map<String, String>> discountPrice = discountService.getDiscountPrice("AAAA3",1L);

        assertEquals(HttpStatus.OK, discountPrice.getStatusCode());
        assertEquals("2.0",discountPrice.getBody().get("Price"));

    }
}

