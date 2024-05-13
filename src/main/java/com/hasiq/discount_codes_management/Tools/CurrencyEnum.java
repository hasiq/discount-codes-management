package com.hasiq.discount_codes_management.Tools;

public enum CurrencyEnum {
    PLN,
    EUR,
    USD,
    JPY,
    GBP,
    CNH,
    AUD,
    CAD,
    CHF,
    HKD,
    NZD,
    ;

    public static CurrencyEnum getById(byte id){
        CurrencyEnum[] enums = CurrencyEnum.values();
            if(id >= 0 && id < enums.length){
                return enums[id];
            }
            else {
                throw new IllegalArgumentException("Invalid Currency ID");
            }

    }
}

