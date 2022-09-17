package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {
    @JsonProperty("no")
    private String rateId;
    @JsonProperty("bid")
    private double bidValue;
    @JsonProperty("ask")
    private double askValue;
    @JsonProperty("effectiveDate")
    private String date;
}
