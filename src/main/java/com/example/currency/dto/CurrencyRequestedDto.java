package com.example.currency.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CurrencyRequestedDto {
    private double bidValue;
    private double askValue;
    private String date;
}
