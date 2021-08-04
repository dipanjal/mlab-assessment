package com.mlab.assessment.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author dipanjal
 * @since version 1
 */

public class DateTimeUtils {

    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String API_DATE_FORMAT = "dd-MM-yyyy";

    public static String toDBDateFormat(String apiDateStr){
        return convertDateFormat(apiDateStr, API_DATE_FORMAT, DB_DATE_FORMAT);
    }

    public static String toAPIDateFormat(String dbDateStr){
        return convertDateFormat(dbDateStr, DB_DATE_FORMAT, API_DATE_FORMAT);
    }

    private static String convertDateFormat(String source, String sourceFormat, String targetFormat){
        LocalDate sourceDate = LocalDate.parse(source, DateTimeFormatter.ofPattern(sourceFormat));
        return sourceDate.format(DateTimeFormatter.ofPattern(targetFormat));
    }


    public static LocalDate formatDateStrToLocalDate(String dateTimeStr, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(dateTimeStr, dateTimeFormatter);
    }

    public static String formatDate(Date date, String format) {
        DateFormat dateFormatter = new SimpleDateFormat(format);
        return date == null ? "" : dateFormatter.format(date);
    }

    public static String formatLocalDateToDateStr(LocalDate localDate, String dateFormat) {
        if (localDate == null) {
            return "";
        }
        return localDate.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static LocalDateTime formatDateStrToLocalDateTime(String dateTimeStr, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
    }

    public static String formatLocalDateTimeToDateStr(LocalDateTime localDateTime, String dateFormat) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static LocalDate addDay(LocalDate localDate, long day) {
        return localDate.plusDays(day);
    }

    public static LocalDateTime addDay(LocalDateTime localDateTime, long day) {
        return localDateTime.plusDays(day);
    }

    public static LocalDateTime addMinute(LocalDateTime localDateTime, long minutes) {
        return localDateTime.plusMinutes(minutes);
    }

    public static LocalDateTime minusDay(LocalDateTime dateTime, long noOfDays){
        return dateTime.minusDays(noOfDays);
    }

    public static Date getDayBefore(long noOfDays){
        return convertToDate(minusDay(LocalDateTime.now(), noOfDays));
    }

    public static Date convertToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
        );
    }

    public static Date convertToDate(LocalDate localDate){
        return convertToDate(localDate.atStartOfDay());
    }

    public static boolean isValidLocalDateTime(LocalDateTime requestDateTime, long maxAllowedDay) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return (requestDateTime.isAfter(currentDateTime)
                && requestDateTime.isBefore(currentDateTime.plusDays(maxAllowedDay)));
    }

    public static boolean isValidLocalDate(LocalDate requestDate, long maxAllowedDay) {
        LocalDate currentDate = LocalDate.now();
        return (requestDate.compareTo(currentDate) >= 0
                && requestDate.isBefore(currentDate.plusDays(maxAllowedDay + 1)));
    }

    public static boolean isNotValidLocalDateTime(LocalDateTime requestDateTime, long maxAllowedDay) {
        return !isValidLocalDateTime(requestDateTime, maxAllowedDay);
    }

    public static boolean isNotValidLocalDate(LocalDate requestDate, long maxAllowedDay) {
        return !isValidLocalDate(requestDate, maxAllowedDay);
    }

    public static boolean isValidWithYearRange(LocalDateTime requestDate, long maxAllowedYears) {
        long userAgeInYear = ChronoUnit.DAYS.between(requestDate, LocalDateTime.now()) / 365;
        return userAgeInYear < maxAllowedYears;
    }

    public static String changeDateFormat(String date, String currentFormat, String expectedFormat) {
        DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern(currentFormat);
        DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern(expectedFormat);
        LocalDate originalDate = LocalDate.parse(date, originalFormat);
        return targetFormat.format(originalDate);
    }

    public static boolean isValidDateRangeLimitForCardStatement(String fromDate, String toDate, Long cardStatementDateRange) {
        LocalDate startDate = formatDateStrToLocalDate(fromDate, DB_DATE_FORMAT);
        LocalDate endDate = formatDateStrToLocalDate(toDate, DB_DATE_FORMAT);
        long daysBetween = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();
        return daysBetween < cardStatementDateRange;
    }

    public static boolean isStartDateGreaterThanEndDate(String fromDate, String toDate) {
        LocalDate startDate = formatDateStrToLocalDate(fromDate, DB_DATE_FORMAT);
        LocalDate endDate = formatDateStrToLocalDate(toDate, DB_DATE_FORMAT);
        return endDate.isBefore(startDate);
    }
}
