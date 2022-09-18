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
        startDateArray[2] = "2022-09-15";
        startDateArray[3] = "2022-09-18";
        startDateArray[4] = "2022-09-15";

        String[] endDateArray = new String[5];
        endDateArray[0] = "2022-09-11";
        endDateArray[1] = "2022-09-11";
        endDateArray[2] = "2022-09-16";
        endDateArray[3] = "2022-09-11";
        endDateArray[4] = "2023-09-30";

        CurrencyResponseDto currencyResponseDto = new CurrencyResponseDto(4.6738, 4.7682, "2022-09-15", -0.0944);
        CurrencyResponseDto currencyResponseDto1 = new CurrencyResponseDto(4.6777, 4.7723, "2022-09-16", -0.0946);
        List<CurrencyResponseDto> currencyResponseDtoList = new ArrayList<>();
        currencyResponseDtoList.add(currencyResponseDto);
        currencyResponseDtoList.add(currencyResponseDto1);

        CurrencyService currencyService = new CurrencyService();

        ResponseEntity<?> responseEntity = currencyService.splitData(startDateArray[0], endDateArray[0]);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity.getBody(), "no data in this time");

        ResponseEntity<?> responseEntity1 = currencyService.splitData(startDateArray[1], endDateArray[1]);
        assertEquals(responseEntity1.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity1.getBody(), "no data in this time");

        ResponseEntity<?> responseEntity2 = currencyService.splitData(startDateArray[2], endDateArray[2]);
        assertEquals(responseEntity2.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity2.getBody()).toString(), currencyResponseDtoList.toString());

        ResponseEntity<?> responseEntity3 = currencyService.splitData(startDateArray[3], endDateArray[3]);
        assertEquals(responseEntity3.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity3.getBody(), "wrong data range");

        ResponseEntity<?> responseEntity4 = currencyService.splitData(startDateArray[4], endDateArray[4]);
        assertEquals(responseEntity4.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(responseEntity4.getBody()).toString(), currencyResponseDtoList.toString());
    }
}
