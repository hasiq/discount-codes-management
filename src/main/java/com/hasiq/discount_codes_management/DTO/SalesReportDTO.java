package com.hasiq.discount_codes_management.DTO;

import com.hasiq.discount_codes_management.Tools.CurrencyEnum;
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
