package com.hasiq.discount_codes_management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "PromoCode")
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeEntity {

    @Id
    private String code;

    private Double discount;

    private String currency;

    private LocalDateTime expirationDate;

    private int maxUsages;

}
