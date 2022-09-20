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
        String[] startDateArray = new String[5];
        startDateArray[0] = "2022-09-10";
        startDateArray[1] = "2022-09-10";
        startDateArray[2] = "2002-01-02";
        startDateArray[3] = "2022-09-18";
        startDateArray[4] = "2000-01-01";

        String[] endDateArray = new String[5];
        endDateArray[0] = "2022-09-11";
        endDateArray[1] = "2022-09-11";
        endDateArray[2] = "2002-01-03";
        endDateArray[3] = "2022-09-11";
        endDateArray[4] = "2002-01-03";

        CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto(3.9451, 4.0249, "2002-01-02", -0.0798);
        CurrencyResponseDto currencyResponseDto1 = new CurrencyResponseDto(3.8994, 3.9782, "2002-01-03", -0.0788);
        List<CurrencyResponseDto> currencyResponseDtoList = new ArrayList<>();
        currencyResponseDtoList.add(currencyResponseDto);
        currencyResponseDtoList.add(currencyResponseDto1);

        CurrencyService currencyService = new CurrencyService();

        //when
        ResponseEntity<?> responseEntity = currencyService.splitData(startDateArray[0], endDateArray[0]);
        //then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity.getBody(), "no data in this time");

        //when
        ResponseEntity<?> responseEntity1 = currencyService.splitData(startDateArray[1], endDateArray[1]);
        //then
        assertEquals(responseEntity1.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity1.getBody(), "no data in this time");

        //when
        ResponseEntity<?> responseEntity2 = currencyService.splitData(startDateArray[2], endDateArray[2]);
        //then
        assertEquals(responseEntity2.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity2.getBody()).toString(), currencyResponseDtoList.toString());

        //when
        ResponseEntity<?> responseEntity3 = currencyService.splitData(startDateArray[3], endDateArray[3]);
        //then
        assertEquals(responseEntity3.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity3.getBody(), "wrong data range");

        //when
        ResponseEntity<?> responseEntity4 = currencyService.splitData(startDateArray[4], endDateArray[4]);
        //then
        assertEquals(responseEntity4.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity4.getBody()).toString(), currencyResponseDtoList.toString());
    }
}
