package com.hasiq.discount_codes_management.ServiceTests;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertEquals(HttpStatus.OK,productService.findAll().getStatusCode());
    }

    @Test
    public void shouldSaveProduct() {
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);

        when(productRepository.save(productEntity)).thenReturn(productEntity);

        assertEquals(HttpStatus.CREATED, productService.save(productEntity).getStatusCode());
    }

    @Test
    public void shouldUpdateProduct() {
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);
        ProductEntity productEntity2 = new ProductEntity("Banana",2.0,"aaa", CurrencyEnum.USD);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.OK, productService.update(productEntity2,1L).getStatusCode());
        assertEquals("Banana",productEntity.getName());
    }

    @Test
    public void shouldReturnNotFoundWhenProductNotFound(){
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", CurrencyEnum.PLN);
        ProductEntity productEntity2 = new ProductEntity("Banana",2.0,"aaa", CurrencyEnum.USD);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.NOT_FOUND, productService.update(productEntity2,3L).getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenProductHaveNoName() {
        ProductEntity productEntity = new ProductEntity(null,2.0,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.BAD_REQUEST, productService.save(productEntity).getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenProductHaveNoCurrency() {
        ProductEntity productEntity = new ProductEntity("Apple",2.0,"aaa", null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.BAD_REQUEST, productService.save(productEntity).getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenProductHaveNoPrice() {
        ProductEntity productEntity = new ProductEntity("Apple",null,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.BAD_REQUEST, productService.save(productEntity).getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenProductPriceIsLessThanZero() {
        ProductEntity productEntity = new ProductEntity("Apple",-1D,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.BAD_REQUEST, productService.save(productEntity).getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestWhenProductNameIsBlank() {
        ProductEntity productEntity = new ProductEntity("",1D,"aaa", CurrencyEnum.PLN);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));

        assertEquals(HttpStatus.BAD_REQUEST, productService.save(productEntity).getStatusCode());
    }

}
