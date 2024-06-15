package com.hasiq.discount_codes_management.ServiceTests;

import com.hasiq.discount_codes_management.Exceptions.BadRequestException;
import com.hasiq.discount_codes_management.Exceptions.NotFoundException;
import com.hasiq.discount_codes_management.entity.ProductEntity;
import com.hasiq.discount_codes_management.repository.ProductRepository;
import com.hasiq.discount_codes_management.service.ProductService;
import com.hasiq.discount_codes_management.tools.CurrencyEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
   public void shouldReturnAllProducts() {

        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);

        when(productRepository.findAll()).thenReturn(List.of(productEntity));

        List<ProductEntity> products = productRepository.findAll();

        assertNotNull(products);
    }

    @Test
    public void shouldSaveProduct() {
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);

        when(productRepository.save(productEntity)).thenReturn(productEntity);

        assertNotNull(productService.save(productEntity));
    }

    @Test
    public void shouldUpdateProduct() {
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);
        ProductEntity productEntity2 = new ProductEntity("Banana",2.0,"aaa", CurrencyEnum.USD);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        assertEquals("Banana",productEntity.getName());
    }

    @Test
    public void shouldReturnNotFoundWhenProductNotFound(){
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);
        ProductEntity productEntity2 = new ProductEntity("Banana",2.0,"aaa", CurrencyEnum.USD);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertThrows(NotFoundException.class, () -> productService.update(productEntity,3L));
    }

    @Test
    public void shouldReturnBadRequestWhenProductHaveNoName() {
        ProductEntity productEntity = new ProductEntity(null,2.0,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertThrows(BadRequestException.class, () -> productService.save(productEntity));
    }

    @Test
    public void shouldReturnBadRequestWhenProductHaveNoCurrency() {
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertThrows(BadRequestException.class, () -> productService.save(productEntity));
    }

    @Test
    public void shouldReturnBadRequestWhenProductHaveNoPrice() {
        ProductEntity productEntity = new ProductEntity("Apple",null,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertThrows(BadRequestException.class, () -> productService.save(productEntity));
    }

    @Test
    public void shouldReturnBadRequestWhenProductPriceIsLessThanZero() {
        ProductEntity productEntity = new ProductEntity("Apple",-1D,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertThrows(BadRequestException.class, () -> productService.save(productEntity));
    }

    @Test
    public void shouldReturnBadRequestWhenProductNameIsBlank() {
        ProductEntity productEntity = new ProductEntity("",1D,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertThrows(BadRequestException.class, () -> productService.save(productEntity));
    }

}
