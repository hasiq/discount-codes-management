package com.hasiq.discount_codes_management.dto;

import com.hasiq.discount_codes_management.tools.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDTO {

    private CurrencyEnum currency;

    private Double totalDiscount;

    private Double totalAmount;

    private int numberOfPurchases;
}
