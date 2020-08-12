package com.humuson.imc.admin.web.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/*
* 두 날짜의 차이 월수를 계산한다.
* year :  Y   => Y*12,
* month:  M   => M,
* day  :  D>0 => 1
*/
public class MonthDiffUtil {

    public static int getMonthDiff(String startDate, String endDate, String pattern) {
        return getMonthDiff(
            LocalDate.parse(startDate, DateTimeFormatter.ofPattern(pattern)),
            LocalDate.parse(endDate, DateTimeFormatter.ofPattern(pattern))
        );
    }

    public static int getMonthDiff(LocalDate startDate, LocalDate endDate) {
        Period diff1 = Period.between(startDate, endDate.plusDays(1));

        int year = diff1.getYears();
        int month = diff1.getMonths();
        int day = diff1.getDays();

        return (year * 12) + (month) + (day > 0 ? 1 : 0);
    }
}
