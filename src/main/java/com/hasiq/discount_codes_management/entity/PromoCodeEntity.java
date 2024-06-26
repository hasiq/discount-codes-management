package com.hasiq.discount_codes_management.entity;

import com.hasiq.discount_codes_management.tools.CurrencyEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "PromoCode")
@NoArgsConstructor
public class PromoCodeEntity {

    @Id
    private String code;

    @Column(nullable = false)
    private Double discount;

    private CurrencyEnum currency;

    private LocalDate expirationDate;

    private int maxUsages;

    private int leftUsages;

    private Boolean isPercent = false;

    public PromoCodeEntity(String code, Double discount, CurrencyEnum currency, LocalDate expirationDate, int maxUsages, boolean isPercent) {
        this.code = code;
        this.discount = discount;
        this.currency = currency;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
        this.isPercent = isPercent;
    }
}
