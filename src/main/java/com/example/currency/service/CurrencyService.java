package com.example.currency.service;

import com.example.currency.dto.CurrencyResponseDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CurrencyService {
    private static final String bankApiUrl = "https://api.nbp.pl/api/exchangerates/rates/c/usd/";

    public ResponseEntity<?> getData(String startDate, String endDate) {
        String bankApiUrlWithDates = bankApiUrl + startDate + "/" + endDate + "/" + "?format=json";
        Object data = getObjectInfoFromApi(bankApiUrlWithDates);
        if (Objects.equals(data.toString(), "400 BAD_REQUEST")) {
            return new ResponseEntity<>("bledny zakres dat", HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = (JSONObject) data;
        List<CurrencyResponseDto> currencyResponseDtoList = getListOfData(jsonObject);
        return new ResponseEntity<>(currencyResponseDtoList, HttpStatus.OK);
    }

    private List<CurrencyResponseDto> getListOfData(JSONObject data) {
        Gson gson = new Gson();
        String json = gson.toJson(data.get("rates"));
        List<CurrencyResponseDto> currencyResponseDtoList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Currency[] currencies = objectMapper.readValue(json, Currency[].class);
            for (Currency currency : currencies) {
                currencyResponseDtoList.add(CurrencyMapper.objectToResponseDto(currency));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyResponseDtoList;
    }

    private Object getObjectInfoFromApi(String url) throws HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, JSONObject.class);
        } catch (HttpClientErrorException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
