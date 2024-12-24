package com.swimming_pool.management.service;

import com.swimming_pool.management.model.entity.Order;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Сервис для управления сущностью {@link Order}
 */
public interface OrderService {

    /**
     * Получение записи по его идентификатору
     *
     * @param id идентификатор записи
     * @return запись
     */
    Order getById(Long id);

    /**
     * Получение ассоциативного массива, содержащего занятые записями временные слоты на определённую дату
     *
     * @param date дата для поиска занятых записей
     * @return ассоциативный массив, содержащий пары: время и количество занятых записей на это время, на основе даты
     */
    Map<LocalTime, Integer> getReservedSlotCountsForDate(LocalDate date);

    /**
     * Получение ассоциативного массива, содержащего свободные для записей временные слоты на определённую дату
     *
     * @param date дата для поиска свободных записей
     * @return ассоциативный массив, содержащий пары: время и количество свободных записей на это время, на основе даты
     */
    Map<LocalTime, Integer> getAvailableSlotCountsForDate(LocalDate date);

    /**
     * Получение списка записей клиента по его ФИО
     *
     * @param clientName ФИО клиента
     * @return список записей клиента по указанному ФИО
     */
    List<Order> getByClientName(String clientName);

    /**
     * Получение списка записей на определённую дату
     *
     * @param date дата для поиска записей
     * @return список записей на указанную дату
     */
    List<Order> getByDate(LocalDate date);

    /**
     * Добавление записи для клиента на определённые дату и время
     *
     * @param order запись для добавления в систему
     * @param clientId идентификатор клиента
     * @return добавленная запись
     */
    Order reserve(Order order, Long clientId);

    /**
     * Добавление записей для клиента с определённых даты и времени на несколько часов подряд
     *
     * @param order запись для добавления в систему
     * @param clientId идентификатор клиента
     * @param hourCount количество часов для записей
     * @return список добавленных записей
     */
    List<Order> reserveForMultiHours(Order order, Long clientId, Integer hourCount);

    /**
     * Отмена записи по идентификаторам клиента и самой записи
     *
     * @param clientId идентификатор клиента
     * @param orderId идентификатор записи
     */
    void cancelReservation(Long clientId, Long orderId);

}
