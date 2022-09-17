package com.example.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CurrencyResponseDto {
    private double bidValue;
    private double askValue;
    private String dayDate;
    private double bidAskDifference;
}
