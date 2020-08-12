package com.humuson.imc.admin.web.util;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MonthDiffUtilTests {

    @BeforeAll
    public static void setUp() {
    }

    @BeforeEach
    public void init() {
    }

    @AfterEach
    public void tearDown() {
    }

    @AfterAll
    public static void cleanUp() {
    }

    @Test
    @DisplayName("LocalDate로 된 두 날짜의 차이 월수를 계산한다. (예상 차이 월수 0)")
    public void testGetMonthDiff0WithLocalDate() {
        // arrange
        LocalDate startDate = LocalDate.parse("2020-01-01");
        LocalDate endDate = LocalDate.parse("2019-12-31");

        int expected = 0;

        // act
        int actual = MonthDiffUtil.getMonthDiff(startDate, endDate);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("LocalDate로 된 두 날짜의 차이 월수를 계산한다. (예상 차이 월수 1)")
    public void testGetMonthDiff1WithLocalDate() {
        // arrange
        LocalDate startDate = LocalDate.parse("2019-12-31");
        LocalDate endDate = LocalDate.parse("2020-01-01");

        int expected = 1;

        // act
        int actual = MonthDiffUtil.getMonthDiff(startDate, endDate);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("LocalDate로 된 두 날짜의 차이 월수를 계산한다. (예상 차이 월수 4)")
    public void testGetMonthDiff4WithLocalDate() {
        // arrange
        LocalDate startDate = LocalDate.parse("2020-02-10");
        LocalDate endDate = LocalDate.parse("2020-06-01");

        int expected = 4;

        // act
        int actual = MonthDiffUtil.getMonthDiff(startDate, endDate);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("문자열로 된 두 날짜의 차이 월수를 계산한다. (예상 차이 월수 1)")
    public void testGetMonthDiff1WithStringDate() {
        // arrange
        String pattern = "yyyy-MM-dd";
        String startDate = "2019-12-31";
        String endDate = "2019-12-31";

        int expected = 1;

        // act
        int actual = MonthDiffUtil.getMonthDiff(startDate, endDate, pattern);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("문자열로 된 두 날짜의 차이 월수를 계산한다. (예상 차이 월수: 12)")
    public void testGetMonthDiff12WithStringDate() {
        // arrange
        String pattern = "yyyyMMdd";
        String startDate = "20190711";
        String endDate = "20200710";

        int expected = 12;

        // act
        int actual = MonthDiffUtil.getMonthDiff(startDate, endDate, pattern);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("문자열로 된 두 날짜의 차이 월수를 계산한다. (예상 차이 월수: 13)")
    public void testGetMonthDiff13WithStringDate() {
        // arrange
        String pattern = "yyyyMMdd";
        String startDate = "20190711";
        String endDate = "20200711";

        int expected = 13;

        // act
        int actual = MonthDiffUtil.getMonthDiff(startDate, endDate, pattern);

        // assert
        assertEquals(expected, actual);
    }
}
