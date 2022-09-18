package com.example.currency.service;

import com.example.currency.model.dto.CurrencyResponseDto;
import com.example.currency.mapper.CurrencyMapper;
import com.example.currency.model.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.example.currency.Constants.bankApiUrl;
import static com.example.currency.Constants.maxDaysDataRange;

@Service
public class CurrencyService {

    public ResponseEntity<?> splitData(String startDate, String endDate) {
        DateService dateService = new DateService(startDate, endDate);
        LocalDate localStartDate = dateService.getLocalStartDate();
        LocalDate localEndDate = dateService.getLocalEndDate();
        if (dateService.getIsDataAvailable() || dateService.isDataOnlyFromWeekend()) {
            return new ResponseEntity<>("no data in this time", HttpStatus.NOT_FOUND);
        }
        List<CurrencyResponseDto> currencyResponseDtoList = new ArrayList<>();
        for (long i = 0; i < dateService.getCounter(); i++) {
            String bankApiUrlWithDates = getApiUrlWithDates(localStartDate.plusDays(maxDaysDataRange * i), localStartDate.plusDays(maxDaysDataRange * (i + 1) - 1));
            if (!addDataToResponseList(bankApiUrlWithDates, currencyResponseDtoList)) {
                return new ResponseEntity<>("wrong data range", HttpStatus.BAD_REQUEST);
            }
        }
        String bankApiUrlWithDates = getApiUrlWithDates(localEndDate.minusDays(dateService.getRest()), localEndDate);
        if(!addDataToResponseList(bankApiUrlWithDates, currencyResponseDtoList)) {
            return new ResponseEntity<>("wrong data range", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(currencyResponseDtoList, HttpStatus.OK);
    }

    private String getApiUrlWithDates(LocalDate start, LocalDate end) {
        return bankApiUrl + start + "/" + end + "/" + "?format=json";
    }

    private boolean addDataToResponseList(String bankApiUrlWithDates, List<CurrencyResponseDto> currencyResponseDtoList){
        Object data = getObjectInfoFromApi(bankApiUrlWithDates);
        if (Objects.equals(data.toString(), "400 BAD_REQUEST")) {
            return false;
        }
        for (Currency currency : Objects.requireNonNull(getCurrencyArrayFromJson((JSONObject) data))) {
            currencyResponseDtoList.add(CurrencyMapper.objectToResponseDto(currency));
        }
        return true;
    }

    private Object getObjectInfoFromApi(String url) throws HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, JSONObject.class);
        } catch (HttpClientErrorException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private Currency[] getCurrencyArrayFromJson(JSONObject data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonObjectToString(data), Currency[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String jsonObjectToString(JSONObject data) {
        Gson gson = new Gson();
        return gson.toJson(data.get("rates"));
    }
}
