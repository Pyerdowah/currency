package com.example.currency;

import java.time.LocalDate;

public class Constants {
    public static final long maxDaysDataRange = 367;
    public static final String bankApiUrl = "https://api.nbp.pl/api/exchangerates/rates/c/usd/";
    public static final LocalDate availableDataFirstDay = LocalDate.of(2002, 1, 2);
}
