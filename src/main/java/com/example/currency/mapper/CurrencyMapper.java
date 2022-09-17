package com.example.currency.mapper;

import com.example.currency.CurrencyApplication;
import com.example.currency.dto.CurrencyRequestedDto;
import com.example.currency.dto.CurrencyResponseDto;
import com.example.currency.model.Currency;

public class CurrencyMapper {

    public static Currency requestedDtoToObject(CurrencyRequestedDto currencyRequestedDto) {
        return Currency.builder()
                .bidValue(currencyRequestedDto.getBidValue())
                .askValue(currencyRequestedDto.getAskValue())
                .date(currencyRequestedDto.getDate())
                .build();
    }

    public static CurrencyResponseDto objectToResponseDto(Currency currency) {
        return CurrencyResponseDto.builder()
                .bidValue(currency.getBidValue())
                .askValue(currency.getAskValue())
                .dayDate(currency.getDate())
                .bidAskDifference(Math.round((currency.getBidValue() - currency.getAskValue()) * 10000.0) / 10000.0)
                .build();
    }
}
