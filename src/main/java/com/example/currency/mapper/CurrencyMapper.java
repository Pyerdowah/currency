package com.example.currency.mapper;

import com.example.currency.model.Currency;
import com.example.currency.model.dto.CurrencyResponseDto;

public class CurrencyMapper {

    public static CurrencyResponseDto objectToResponseDto(Currency currency) {
        return CurrencyResponseDto.builder()
                .bidValue(currency.getBidValue())
                .askValue(currency.getAskValue())
                .dayDate(currency.getDate())
                .bidAskDifference(Math.round((currency.getBidValue() - currency.getAskValue()) * 10000.0) / 10000.0)
                .build();
    }
}
