package com.example.currency.service;

import com.example.currency.Constants;
import org.junit.Test;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

public class DateServiceTest {

    @Test
    public void date_service() {
        //given
        String[] startDateArray = {"2022-09-11", "2000-09-11", "2023-09-11", "2023-09-11", "2023-09-11", "2000-09-11"};
        String[] endDateArray = {"2022-09-18", "2022-09-16", "2000-09-16", "2022-09-17", "2023-09-15", "2000-10-11"};
        LocalDate[] startDateTest = {LocalDate.parse(startDateArray[0]), Constants.availableDataFirstDay, LocalDate.parse(startDateArray[2]), LocalDate.parse(startDateArray[3]), LocalDate.parse(startDateArray[4]), Constants.availableDataFirstDay};
        LocalDate[] endDateTest = {LocalDate.parse(endDateArray[0]), LocalDate.parse(endDateArray[1]), LocalDate.parse(endDateArray[2]), LocalDate.parse(endDateArray[3]), LocalDate.now(), LocalDate.parse(endDateArray[5])};
        Boolean[] dataAvailableTest = {false, false, false, false, true, true};
        Long[] daysBetweenTest = {
                DAYS.between(LocalDate.parse(startDateArray[0]), LocalDate.parse(endDateArray[0])),
                DAYS.between(Constants.availableDataFirstDay, LocalDate.parse(endDateArray[1])),
                DAYS.between(LocalDate.parse(startDateArray[2]), LocalDate.parse(endDateArray[2])),
                DAYS.between(LocalDate.parse(startDateArray[3]), LocalDate.parse(endDateArray[3])),
                DAYS.between(LocalDate.parse(startDateArray[4]), LocalDate.parse(LocalDate.now().toString())),
                DAYS.between(Constants.availableDataFirstDay, LocalDate.parse(endDateArray[5]))};
        Long[] counterTest = {
                DAYS.between(LocalDate.parse(startDateArray[0]), LocalDate.parse(endDateArray[0])) / Constants.maxDaysDataRange,
                DAYS.between(Constants.availableDataFirstDay, LocalDate.parse(endDateArray[1])) / Constants.maxDaysDataRange,
                DAYS.between(LocalDate.parse(startDateArray[2]), LocalDate.parse(endDateArray[2])) / Constants.maxDaysDataRange,
                DAYS.between(LocalDate.parse(startDateArray[3]), LocalDate.parse(endDateArray[3])) / Constants.maxDaysDataRange,
                DAYS.between(LocalDate.parse(startDateArray[4]), LocalDate.parse(LocalDate.now().toString())) / Constants.maxDaysDataRange,
                DAYS.between(Constants.availableDataFirstDay, LocalDate.parse(endDateArray[5])) / Constants.maxDaysDataRange};
        Long[] restTest = {
                DAYS.between(LocalDate.parse(startDateArray[0]), LocalDate.parse(endDateArray[0])) % Constants.maxDaysDataRange,
                DAYS.between(Constants.availableDataFirstDay, LocalDate.parse(endDateArray[1])) % Constants.maxDaysDataRange,
                DAYS.between(LocalDate.parse(startDateArray[2]), LocalDate.parse(endDateArray[2])) % Constants.maxDaysDataRange,
                DAYS.between(LocalDate.parse(startDateArray[3]), LocalDate.parse(endDateArray[3])) % Constants.maxDaysDataRange,
                DAYS.between(LocalDate.parse(startDateArray[4]), LocalDate.parse(LocalDate.now().toString())) % Constants.maxDaysDataRange,
                DAYS.between(Constants.availableDataFirstDay, LocalDate.parse(endDateArray[5])) % Constants.maxDaysDataRange};

        for (int i = 0; i < 5; i++) {
            //when
            DateService dateService = new DateService(startDateArray[i], endDateArray[i]);
            //then
            assertEquals(dateService.getLocalStartDate(), startDateTest[i]);
            assertEquals(dateService.getLocalEndDate(), endDateTest[i]);
            assertEquals(dateService.getToday(), LocalDate.now());
            assertEquals(dateService.getIsDataAvailable(), dataAvailableTest[i]);
            assertEquals(dateService.getDaysBetweenStartAndEnd(), daysBetweenTest[i]);
            assertEquals(dateService.getCounter(), counterTest[i]);
            assertEquals(dateService.getRest(), restTest[i]);
        }
    }

    @Test
    public void is_data_only_from_weekend_to_false() {
        //given
        String[] startDateArray = new String[7];
        startDateArray[0] = "2022-09-11";
        startDateArray[1] = "2022-09-09";
        startDateArray[2] = "2022-09-09";
        startDateArray[3] = "2022-09-11";
        startDateArray[4] = "2022-09-15";
        startDateArray[5] = "2022-09-16";
        startDateArray[6] = "2022-09-11";

        String[] endDateArray = new String[7];
        endDateArray[0] = "2022-09-18";
        endDateArray[1] = "2022-09-16";
        endDateArray[2] = "2022-09-18";
        endDateArray[3] = "2022-09-16";
        endDateArray[4] = "2022-09-16";
        endDateArray[5] = "2022-09-17";
        endDateArray[6] = "2022-09-15";

        for (int i = 0; i < 7; i++) {
            //when
            DateService dateService = new DateService(startDateArray[i], endDateArray[i]);
            //then
            assertFalse(dateService.isDataOnlyFromWeekend());
        }
    }

    @Test
    public void is_data_only_from_weekend_to_true() {
        //given
        String[] startDateArray = new String[2];
        startDateArray[0] = "2022-09-18";
        startDateArray[1] = "2022-09-17";

        String[] endDateArray = new String[2];
        endDateArray[0] = "2022-09-18";
        endDateArray[1] = "2022-09-18";

        for (int i = 0; i < 2; i++) {
            //when
            DateService dateService = new DateService(startDateArray[i], endDateArray[i]);
            //then
            assertTrue(dateService.isDataOnlyFromWeekend());
        }
    }

    @Test
    public void only_today_data_to_true() {
        DateService dateService = new DateService(LocalDate.parse(LocalDate.now().toString()).toString(), LocalDate.parse(LocalDate.now().toString()).toString());
        assertTrue(dateService.onlyTodayData());
    }

    @Test
    public void only_today_data_to_false() {
        DateService dateService = new DateService("2020-09-19", LocalDate.parse(LocalDate.now().toString()).toString());
        assertFalse(dateService.onlyTodayData());
    }
}
