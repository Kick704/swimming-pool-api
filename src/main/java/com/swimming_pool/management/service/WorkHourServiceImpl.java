package com.swimming_pool.management.service;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;
import com.swimming_pool.management.model.entity.WorkHour;
import com.swimming_pool.management.repository.HolidayRepository;
import com.swimming_pool.management.repository.WorkHourRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * Реализация сервиса для управления сущностью {@link WorkHour}
 */
@Service
public class WorkHourServiceImpl implements WorkHourService {

    /**
     * Репозиторий для управления рабочими часами(графиком) в БД
     */
    private final WorkHourRepository workHourRepository;

    /**
     * Репозиторий для управления праздничными днями в БД
     */
    private final HolidayRepository holidayRepository;

    /**
     * Конструктор для инициализации {@link WorkHourRepository} и {@link HolidayRepository}
     *
     * @param workHourRepository репозиторий для управления рабочими часами(графиком) в БД
     * @param holidayRepository репозиторий для управления праздничными днями в БД
     */
    public WorkHourServiceImpl(WorkHourRepository workHourRepository, HolidayRepository holidayRepository) {
        this.workHourRepository = workHourRepository;
        this.holidayRepository = holidayRepository;
    }

    /**
     * Получение дневного лимита для клиентов на определённую дату
     *
     * @param date дата для определения дневного лимита для клиентов
     * @return дневной лимит для клиентов на указанную дату
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getDailyLimitPerClients(LocalDate date) {
        return getWorkHour(date).getDailyLimitPerClients();
    }

    /**
     * Получение лимита записей в час на определённую дату
     *
     * @param date дата для определения лимит записей в час
     * @return лимит записей в час на указанную дату
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getLimitPerHour(LocalDate date) {
        return getWorkHour(date).getLimitPerHour();
    }

    /**
     * Получение сгенерированного списка таймслотов на определённую дате
     *
     * @param date дата для генерации таймслотов
     * @return сгенерированный список таймслотов на указанную дату
     */
    @Override
    @Transactional(readOnly = true)
    public List<LocalTime> getGeneratedTimeSlotsForDate(LocalDate date) {
        WorkHour workHour = getWorkHour(date);
        LocalTime startTime = date.equals(LocalDate.now()) ?
                LocalTime.now().plusHours(1) : workHour.getStartTime();
        LocalTime endTime = workHour.getEndTime();
        if (!startTime.isBefore(endTime)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "Некорректный временной диапазон рабочего графика"
            );
        }
        return Stream.iterate(startTime, time -> time.isBefore(endTime), time -> time.plusHours(1))
                .toList();
    }

    /**
     * Проверка нахождения даты с временем в рамках рабочего времени(графика)
     *
     * @param dateTime проверяемая дата с временем
     * @return результат проверки
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isWithinWorkHour(@NonNull LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        List<LocalTime> timeSlotsForDate = getGeneratedTimeSlotsForDate(date);
        return timeSlotsForDate.contains(time);
    }

    /**
     * Проверка нахождения интервала времён в рамках рабочего времени(графика) на определённую дату
     *
     * @param date проверяемая дата
     * @param startTime начальное время интервала для проверки
     * @param endTime конечное время интервала для проверки
     * @return результат проверки
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isWithinWorkHourRange(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<LocalTime> timeSlotsForDate = getGeneratedTimeSlotsForDate(date);
        return timeSlotsForDate.contains(startTime) && timeSlotsForDate.contains(endTime.minusHours(1));
    }

    /**
     * Получение рабочих часов(графика) на определённую дату
     *
     * @param date дата для получения рабочих часов(графика)
     * @return рабочие часы(график) на указанную дату
     */
    private WorkHour getWorkHour(LocalDate date) {
        return workHourRepository.findWorkHour(isHoliday(date))
                .orElseThrow(() -> new SwimmingPoolManagementException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "Ошибка инициализации рабочего графика"
                ));
    }

    /**
     * Проверка, что дата является праздничным днём
     *
     * @param date проверяемая дата
     * @return результат проверки
     */
    private boolean isHoliday(LocalDate date) {
        return holidayRepository.findByDate(date).isPresent();
    }

}
