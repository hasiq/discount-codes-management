package com.hasiq.discount_codes_management.ServiceTests;

import com.hasiq.discount_codes_management.Exceptions.BadRequestException;
import com.hasiq.discount_codes_management.Exceptions.NotFoundException;
import com.hasiq.discount_codes_management.dto.SalesReportDTO;
import com.hasiq.discount_codes_management.entity.ProductEntity;
import com.hasiq.discount_codes_management.entity.PromoCodeEntity;
import com.hasiq.discount_codes_management.entity.PurchaseEntity;
import com.hasiq.discount_codes_management.repository.ProductRepository;
import com.hasiq.discount_codes_management.repository.PromoCodeRepository;
import com.hasiq.discount_codes_management.repository.PurchaseRepository;
import com.hasiq.discount_codes_management.service.DiscountService;
import com.hasiq.discount_codes_management.service.PromoCodeService;
import com.hasiq.discount_codes_management.service.PurchaseService;
import com.hasiq.discount_codes_management.tools.CurrencyEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PurchaseServiceTests {

    @Mock
    private  DiscountService discountService;

    @Mock
    private  PurchaseRepository purchaseRepository;

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private PromoCodeService promoCodeService;

    @InjectMocks
    private PurchaseService purchaseService;



    @Test
    public void shouldPurchaseProduct(){
        ProductEntity productEntity = new ProductEntity("Banana", 2.0, "test", CurrencyEnum.PLN);
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("XXXX",1.0,CurrencyEnum.PLN, LocalDate.now(),20,false);
        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        when(discountService.getDiscountPrice("XXXX",1L)).thenReturn(Map.of("Price","1.0"));

        when(promoCodeService.findByCode("XXXX")).thenReturn(promoCodeEntity);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));


        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setRegularPrice(productEntity.getPrice());
        purchase.setCurrency(productEntity.getCurrency());
        purchase.setProductEntity(productEntity);
        purchase.setDiscountPrice(promoCodeEntity.getDiscount());

        promoCodeEntity.setLeftUsages(promoCodeEntity.getLeftUsages() - 1);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertNotNull(purchaseService.purchase(1L,"XXXX"));
    }

    @Test
    public void shouldReturnSalesReport(){
        List<Object[]> salesReport = new ArrayList<>();
        salesReport.add(new Object[]{CurrencyEnum.PLN,2.0,4.0,1});
        when(purchaseRepository.salesReport()).thenReturn(salesReport);

        List<SalesReportDTO> body = new ArrayList<>();
        for(Object[] row : salesReport){
            SalesReportDTO salesReportDTO = new SalesReportDTO();
            salesReportDTO.setCurrency((CurrencyEnum) row[0]);
            salesReportDTO.setTotalDiscount((Double) row[1]);
            salesReportDTO.setTotalAmount((Double) row[2]);
            salesReportDTO.setNumberOfPurchases((Integer) row[3]);
            body.add(salesReportDTO);
        }

        assertNotNull(body);
        assertEquals("PLN",body.get(0).getCurrency().toString());
    }

    @Test
    public void shouldNotPurchaseProduct(){
        ProductEntity productEntity = new ProductEntity("Banana", 2.0, "test", CurrencyEnum.PLN);
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("XXXX",1.0,CurrencyEnum.PLN, LocalDate.now(),20,false);
        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        when(discountService.getDiscountPrice("XXXX",1L)).thenReturn(null);

        when(promoCodeService.findByCode("XXXX")).thenReturn(promoCodeEntity);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));


        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setRegularPrice(productEntity.getPrice());
        purchase.setCurrency(productEntity.getCurrency());
        purchase.setProductEntity(productEntity);
        purchase.setDiscountPrice(promoCodeEntity.getDiscount());

        promoCodeEntity.setLeftUsages(promoCodeEntity.getLeftUsages() - 1);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);

        assertThrows(NotFoundException.class, () -> purchaseService.purchase(1L,"XXXX"));
    }

    @Test
    public void shouldNotPurchaseProductBecauseOfWarning(){
        ProductEntity productEntity = new ProductEntity("Banana", 2.0, "test", CurrencyEnum.PLN);
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("XXXX",1.0,CurrencyEnum.PLN, LocalDate.now(),20,false);
        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        when(discountService.getDiscountPrice("XXXX",1L)).thenReturn(Map.of("Warning","Discount Code Currency Mismatch"));

        when(promoCodeService.findByCode("XXXX")).thenReturn(promoCodeEntity);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));


        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setRegularPrice(productEntity.getPrice());
        purchase.setCurrency(productEntity.getCurrency());
        purchase.setProductEntity(productEntity);
        purchase.setDiscountPrice(promoCodeEntity.getDiscount());

        promoCodeEntity.setLeftUsages(promoCodeEntity.getLeftUsages() - 1);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);

        assertThrows(BadRequestException.class, () -> purchaseService.purchase(1L,"XXXX"));
    }

    @Test
    public void shouldPurchaseProductWithPercentDiscount(){
        ProductEntity productEntity = new ProductEntity("Banana", 2.0, "test", CurrencyEnum.PLN);
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity("XXXX",50.0,CurrencyEnum.PLN, LocalDate.now(),20,true);
        promoCodeEntity.setLeftUsages(20);

        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        when(discountService.getDiscountPrice("XXXX",1L)).thenReturn(Map.of("Price","1.0"));

        when(promoCodeService.findByCode("XXXX")).thenReturn(promoCodeEntity);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));


        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setRegularPrice(productEntity.getPrice());
        purchase.setCurrency(productEntity.getCurrency());
        purchase.setProductEntity(productEntity);
        purchase.setDiscountPrice(promoCodeEntity.getDiscount());

        promoCodeEntity.setLeftUsages(promoCodeEntity.getLeftUsages() - 1);
        when(promoCodeRepository.save(promoCodeEntity)).thenReturn(promoCodeEntity);


        assertNotNull(purchaseService.purchase(1L,"XXXX"));
    }

}
