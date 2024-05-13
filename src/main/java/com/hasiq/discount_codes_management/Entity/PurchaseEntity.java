package com.hasiq.discount_codes_management.Entity;

import com.hasiq.discount_codes_management.Tools.CurrencyEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate purchaseDate;

    private Double regularPrice;

    private Double discountPrice;

    private CurrencyEnum currency;

    @ManyToOne
    private ProductEntity productEntity;

    public PurchaseEntity(LocalDate purchaseDate, Double regularPrice, Double discountPrice, CurrencyEnum currency, ProductEntity productEntity) {
        this.purchaseDate = purchaseDate;
        this.regularPrice = regularPrice;
        this.discountPrice = discountPrice;
        this.currency = currency;
        this.productEntity = productEntity;
    }
}
