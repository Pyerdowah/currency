package com.example.currency.service;

import com.example.currency.Constants;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateServiceTest {

    @Test
    public void date_service() {
        //given
        String[] startDateArray = new String[6];
        startDateArray[0] = "2022-09-11"; //false
        startDateArray[1] = "2000-09-11"; //false
        startDateArray[2] = "2023-09-11"; //true
        startDateArray[3] = "2023-09-11"; //true
        startDateArray[4] = "2023-09-11"; //true
        startDateArray[5] = "2000-09-11"; //false

        String[] endDateArray = new String[6];
        endDateArray[0] = "2022-09-18"; //false //false
        endDateArray[1] = "2022-09-16"; //false //false
        endDateArray[2] = "2000-09-16"; //true //false
        endDateArray[3] = "2022-09-17"; //false //false
        endDateArray[4] = "2023-09-15"; //false //true
        endDateArray[5] = "2000-10-11"; //true // true

        //when
        DateService dateService = new DateService(startDateArray[0], endDateArray[0]);
        DateService dateService1 = new DateService(startDateArray[1], endDateArray[1]);
        DateService dateService2 = new DateService(startDateArray[2], endDateArray[2]);
        DateService dateService3 = new DateService(startDateArray[3], endDateArray[3]);
        DateService dateService4 = new DateService(startDateArray[4], endDateArray[4]);
        DateService dateService5 = new DateService(startDateArray[5], endDateArray[5]);

        //then
        assertEquals(dateService.getLocalStartDate(), LocalDate.parse(startDateArray[0]));
        assertEquals(dateService.getLocalEndDate(), LocalDate.parse(endDateArray[0]));
        assertEquals(dateService.getToday(), LocalDate.now());
        assertFalse(dateService.getIsDataAvailable());
        assertEquals(dateService.getDaysBetweenStartAndEnd(), 7L);
        assertEquals(dateService.getCounter(), 0L);
        assertEquals(dateService.getRest(), 7L);

        assertEquals(dateService1.getLocalStartDate(), Constants.availableDataFirstDay);
        assertEquals(dateService1.getLocalEndDate(), LocalDate.parse(endDateArray[1]));
        assertEquals(dateService1.getToday(), LocalDate.now());
        assertFalse(dateService1.getIsDataAvailable());
        assertEquals(dateService1.getDaysBetweenStartAndEnd(), 7562L);
        assertEquals(dateService1.getCounter(), 20L);
        assertEquals(dateService1.getRest(), 222L);

        assertEquals(dateService2.getLocalStartDate(), LocalDate.parse(startDateArray[2]));
        assertEquals(dateService2.getLocalEndDate(), LocalDate.parse(endDateArray[2]));
        assertEquals(dateService2.getToday(), LocalDate.now());
        assertFalse(dateService2.getIsDataAvailable());
        assertEquals(dateService2.getDaysBetweenStartAndEnd(), -8395L);
        assertEquals(dateService2.getCounter(), -22L);
        assertEquals(dateService2.getRest(), -321L);

        assertEquals(dateService3.getLocalStartDate(), LocalDate.parse(startDateArray[3]));
        assertEquals(dateService3.getLocalEndDate(), LocalDate.parse(endDateArray[3]));
        assertEquals(dateService3.getToday(), LocalDate.now());
        assertFalse(dateService3.getIsDataAvailable());
        assertEquals(dateService3.getDaysBetweenStartAndEnd(), -359L);
        assertEquals(dateService3.getCounter(), 0L);
        assertEquals(dateService3.getRest(), -359L);

        assertEquals(dateService4.getLocalStartDate(), LocalDate.parse(startDateArray[4]));
        assertEquals(dateService4.getLocalEndDate(), dateService4.getToday());
        assertEquals(dateService4.getToday(), LocalDate.now());
        assertTrue(dateService4.getIsDataAvailable());
        assertEquals(dateService4.getDaysBetweenStartAndEnd(), -357L);
        assertEquals(dateService4.getCounter(), 0L);
        assertEquals(dateService4.getRest(), -357L);

        assertEquals(dateService5.getLocalStartDate(), Constants.availableDataFirstDay);
        assertEquals(dateService5.getLocalEndDate(), LocalDate.parse(endDateArray[5]));
        assertEquals(dateService5.getToday(), LocalDate.now());
        assertTrue(dateService5.getIsDataAvailable());
        assertEquals(dateService5.getDaysBetweenStartAndEnd(), -448L);
        assertEquals(dateService5.getCounter(), -1L);
        assertEquals(dateService5.getRest(), -81L);

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
        DateService dateService = new DateService("2022-09-19", "2022-09-19");
        assertTrue(dateService.onlyTodayData());
    }

    @Test
    public void only_today_data_to_false() {
        DateService dateService = new DateService("2020-09-19", "2022-09-19");
        assertFalse(dateService.onlyTodayData());
    }
}
