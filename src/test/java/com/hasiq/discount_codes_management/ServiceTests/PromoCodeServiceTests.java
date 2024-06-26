package com.hasiq.discount_codes_management.ServiceTests;

import com.hasiq.discount_codes_management.Exceptions.BadRequestException;
import com.hasiq.discount_codes_management.Exceptions.NotFoundException;
import com.hasiq.discount_codes_management.entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.service.PromoCodeService;
import com.hasiq.discount_codes_management.tools.CurrencyEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PromoCodeServiceTests {

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @InjectMocks
    private PromoCodeService promoCodeService;

    @Test
    public void shouldReturnListOfPromoCodes() {
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        when(promoCodeRepository.findAll()).thenReturn(List.of(promoCodeEntity));

        assertNotNull(promoCodeService.findAll());
    }

    @Test
    public void shouldReturnPromoCodeById() {
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(true);
        when(promoCodeRepository.findByCode("AAAA")).thenReturn(promoCodeEntity);

        PromoCodeEntity promoCode = promoCodeService.findByCode("AAAA");

        assertNotNull(promoCode);
    }

    @Test
    public void shouldSavePromoCode() {
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertNotNull(promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotFindPromoCode() {

        assertThrows(NotFoundException.class, () -> promoCodeService.findByCode("AAAA"));
    }

    @Test
    public void shouldNotSavePromoCodeWhenLeftUsagesAreMoreThanMaxUsages(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(30);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotSavePromoCodeWhenExpirationDateIsNull(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, CurrencyEnum.PLN, null,20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotSavePromoCodeWhenCurrencyIsNull(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, null, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotSavePromoCodeWhenDiscountIsNull(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",null, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotSavePromoCodeWhenDiscountCodeIsNull(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity(null,2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotSavePromoCodeWhenDiscountCodeIsShorterThan3Characters(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }
    @Test
    public void shouldNotSavePromoCodeWhenDiscountCodeIsLongerThan24Characters(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAAAAAAAAAAAAAAAAAAAAAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }

    @Test
    public void shouldNotSavePromoCodeWhenCodeAlreadyExists(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("AAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(true);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }
    @Test
    public void shouldNotSavePromoCodeWhenCodeIsNotAlphanumeric(){
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("@-AAAA",2.0, CurrencyEnum.PLN, LocalDate.now(),20,false);

        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.existsByCode("AAAA")).thenReturn(false);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertThrows(BadRequestException.class, () -> promoCodeService.save(promoCodeEntity));
    }
}
