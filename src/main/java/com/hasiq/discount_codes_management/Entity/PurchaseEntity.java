package com.hasiq.discount_codes_management.Entity;

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

    private String currency;

    @ManyToOne
    private ProductEntity productEntity;
}
