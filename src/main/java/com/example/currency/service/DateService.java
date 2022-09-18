package com.example.currency.service;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

import static com.example.currency.Constants.availableDataFirstDay;
import static com.example.currency.Constants.maxDaysDataRange;
import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@Setter
public class DateService {
    private LocalDate localStartDate;
    private LocalDate localEndDate;
    private LocalDate today;
    private Long daysBetweenStartAndEnd;
    private Long counter;
    private Long rest;
    private Boolean isDataAvailable;

    public DateService(String localStartDate, String localEndDate) {
        this.localStartDate = LocalDate.parse(localStartDate);
        this.localEndDate = LocalDate.parse(localEndDate);
        this.today = LocalDate.now();
        this.isDataAvailable = isDataAvailableInThisRange();
        setLocalStartDateToFirstAvailableDay();
        setLocalEndDateNotLaterThanToday();
        this.daysBetweenStartAndEnd = DAYS.between(this.localStartDate, this.localEndDate);
        this.counter = daysBetweenStartAndEnd / maxDaysDataRange;
        this.rest = daysBetweenStartAndEnd % maxDaysDataRange;
    }

    private void setLocalStartDateToFirstAvailableDay() {
        if (localStartDate.isBefore(availableDataFirstDay)) {
            localStartDate = availableDataFirstDay;
        }
    }

    private void setLocalEndDateNotLaterThanToday() {
        if (localEndDate.isAfter(today)) {
            localEndDate = today;
        }
    }

    private boolean isDataAvailableInThisRange() {
        return (localStartDate.isAfter(today) || localEndDate.isBefore(availableDataFirstDay))
                && localStartDate.isBefore(localEndDate);
    }

    public boolean isDataOnlyFromWeekend() {
        return (DAYS.between(localStartDate, localEndDate) == 0 || DAYS.between(localStartDate, localEndDate) == 1)
                && isWeekend(localEndDate)
                && isWeekend(localStartDate);
    }

    private boolean isWeekend(LocalDate localDate) {
        DayOfWeek day = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    public boolean onlyTodayData() {
        return (localStartDate.toString().equals(localEndDate.toString()) && localEndDate.toString().equals(today.toString()));
    }
}
