package com.swimming_pool.management.util;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * Утилитарный класс для работы с датой и временем между.
 * <p>Предоставляет статические поля и методы для работы напрямую через класс
 */
public final class DateTimeUtils {

    /**
     * Шаблон даты
     */
    public static final String DATE_REGEXP = "^(\\d{2}\\.\\d{2}\\.\\d{4})$";

    /**
     * Шаблон даты с временем
     */
    public static final String DATETIME_REGEXP = "^(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}):00$";

    /**
     * Формат даты
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Формат времени
     */
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:00");

    /**
     * Формат даты с временем
     */
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:00");

    private DateTimeUtils() {
    }

    /**
     * Преобразование даты и времени из строки в объект {@link LocalDateTime}
     *
     * @param dateTime строка с датой и временем
     * @return объект {@link LocalDateTime}, представляющий указанную дату и время
     */
    public static LocalDateTime parseToLocalDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
        } catch (DateTimeParseException ex) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format("Ошибка обработки даты: %s", dateTime)
            );
        }
    }

    /**
     * Преобразование даты из строки в объект {@link LocalDate}
     *
     * @param date строка с даты
     * @return объект {@link LocalDate}, представляющий указанную дату
     */
    public static LocalDate parseToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format("Ошибка обработки даты: %s", date)
            );
        }
    }

    /**
     * Форматирование объекта {@link LocalDate} в строку с датой
     *
     * @param date объект {@link LocalDate}, представляющий дату
     * @return строка с датой
     */
    public static String formatToDateString(LocalDate date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Форматирование объекта {@link LocalTime} в строку с временем
     *
     * @param time объект {@link LocalTime}, представляющий время
     * @return строка с временем
     */
    public static String formatToTimeString(LocalTime time) {
        return TIME_FORMAT.format(time);
    }

    /**
     * Форматирование объекта {@link LocalDateTime} в строку с датой и временем
     *
     * @param dateTime объект {@link LocalDateTime}, представляющий дату и время
     * @return строка с датой и временем
     */
    public static String formatToDateTimeString(LocalDateTime dateTime) {
        return DATE_TIME_FORMAT.format(dateTime);
    }

}
