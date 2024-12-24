package com.swimming_pool.management.service;

import com.swimming_pool.management.model.entity.WorkHour;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Сервис для управления сущностью {@link WorkHour}
 */
public interface WorkHourService {

    /**
     * Получение дневного лимита для клиентов на определённую дату
     *
     * @param date дата для определения дневного лимита для клиентов
     * @return дневной лимит для клиентов на указанную дату
     */
    Integer getDailyLimitPerClients(LocalDate date);

    /**
     * Получение лимита записей в час на определённую дату
     *
     * @param date дата для определения лимит записей в час
     * @return лимит записей в час на указанную дату
     */
    Integer getLimitPerHour(LocalDate date);

    /**
     * Получение сгенерированного списка таймслотов на определённую дате
     *
     * @param date дата для генерации таймслотов
     * @return сгенерированный список таймслотов на указанную дату
     */
    List<LocalTime> getGeneratedTimeSlotsForDate(LocalDate date);

    /**
     * Проверка нахождения даты с временем в рамках рабочего времени(графика)
     *
     * @param dateTime проверяемая дата с временем
     * @return результат проверки
     */
    boolean isWithinWorkHour(LocalDateTime dateTime);

    /**
     * Проверка нахождения интервала времён в рамках рабочего времени(графика) на определённую дату
     *
     * @param date проверяемая дата
     * @param startTime начальное время интервала для проверки
     * @param endTime конечное время интервала для проверки
     * @return результат проверки
     */
    boolean isWithinWorkHourRange(LocalDate date, LocalTime startTime, LocalTime endTime);

}
