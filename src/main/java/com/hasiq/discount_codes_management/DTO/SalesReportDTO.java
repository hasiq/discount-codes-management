package com.hasiq.discount_codes_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDTO {

    private String currency;

    private Double totalDiscount;

    private Double totalAmount;

    private int numberOfPurchases;
}
