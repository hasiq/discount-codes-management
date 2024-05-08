package com.hasiq.discount_codes_management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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

    private Double discount;

    private String currency;

    private LocalDate expirationDate;

    private int maxUsages;

    private int leftUsages;

    public PromoCodeEntity(String code, Double discount, String currency, LocalDate expirationDate, int maxUsages) {
        this.code = code;
        this.discount = discount;
        this.currency = currency;
        this.expirationDate = expirationDate;
        this.maxUsages = maxUsages;
    }
}
