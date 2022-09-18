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
        if (dateService.onlyTodayData()) {
            String bankApiUrlWithDates = getApiUrlWithDates(localStartDate, localStartDate);
            Object data = getObjectInfoFromApi(bankApiUrlWithDates);
            if (checkApiRequest(data)) {
                return new ResponseEntity<>("no data in this time", HttpStatus.NOT_FOUND);
            }
        }
        List<CurrencyResponseDto> currencyResponseDtoList = new ArrayList<>();
        for (long i = 0; i < dateService.getCounter(); i++) {
            String bankApiUrlWithDates = getApiUrlWithDates(localStartDate.plusDays(maxDaysDataRange * i), localStartDate.plusDays(maxDaysDataRange * (i + 1) - 1));
            Object data = getObjectInfoFromApi(bankApiUrlWithDates);
            if (checkApiRequest(data)) {
                return new ResponseEntity<>("wrong data range", HttpStatus.BAD_REQUEST);
            }
            addDataToResponseList(currencyResponseDtoList, data);
        }
        String bankApiUrlWithDates = getApiUrlWithDates(localEndDate.minusDays(dateService.getRest()), localEndDate);
        Object data = getObjectInfoFromApi(bankApiUrlWithDates);
        if (checkApiRequest(data)) {
            return new ResponseEntity<>("wrong data range", HttpStatus.BAD_REQUEST);
        }
        addDataToResponseList(currencyResponseDtoList, data);
        return new ResponseEntity<>(currencyResponseDtoList, HttpStatus.OK);
    }

    private String getApiUrlWithDates(LocalDate start, LocalDate end) {
        return bankApiUrl + start + "/" + end + "/" + "?format=json";
    }

    private boolean checkApiRequest(Object data) {
        return Objects.equals(data.toString(), "400 BAD_REQUEST");
    }

    private void addDataToResponseList(List<CurrencyResponseDto> currencyResponseDtoList, Object data) {
        for (Currency currency : Objects.requireNonNull(getCurrencyArrayFromJson((JSONObject) data))) {
            currencyResponseDtoList.add(CurrencyMapper.objectToResponseDto(currency));
        }
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
