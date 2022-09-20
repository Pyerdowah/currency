package com.example.currency.service;

import com.example.currency.model.dto.CurrencyResponseDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyServiceTest {

    @Test
    public void split_data() {
        //given
        String[] startDateArray = {"2022-09-10", "2022-09-10", "2002-01-02", "2022-09-18", "2000-01-01"};
        String[] endDateArray = {"2022-09-11", "2022-09-11", "2002-01-03", "2022-09-11", "2002-01-03"};

        CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto(3.9451, 4.0249, "2002-01-02", -0.0798);
        CurrencyResponseDto currencyResponseDto1 = new CurrencyResponseDto(3.8994, 3.9782, "2002-01-03", -0.0788);
        List<CurrencyResponseDto> currencyResponseDtoList = new ArrayList<>();
        currencyResponseDtoList.add(currencyResponseDto);
        currencyResponseDtoList.add(currencyResponseDto1);

        HttpStatus[] httpStatusCodeTest = {HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND, HttpStatus.OK, HttpStatus.BAD_REQUEST, HttpStatus.OK};
        String[] currencyResponseBodyTest = {"no data in this time", "no data in this time", currencyResponseDtoList.toString(), "wrong data range", currencyResponseDtoList.toString()};

        CurrencyService currencyService = new CurrencyService();

        for (int i = 0; i < 5; i++) {
            //when
            ResponseEntity<?> responseEntity = currencyService.splitData(startDateArray[i], endDateArray[i]);
            //then
            assertEquals(responseEntity.getStatusCode(), httpStatusCodeTest[i]);
            assertEquals(Objects.requireNonNull(responseEntity.getBody()).toString(), currencyResponseBodyTest[i]);
        }
    }
}
