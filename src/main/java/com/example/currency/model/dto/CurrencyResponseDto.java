package com.example.currency.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CurrencyResponseDto {
    private double bidValue;
    private double askValue;
    private String dayDate;
    private double bidAskDifference;
}
