package com.swimming_pool.management.facade;

import com.swimming_pool.management.model.dto.request.MultiHourReservationDTO;
import com.swimming_pool.management.model.dto.request.OrderCancellationDTO;
import com.swimming_pool.management.model.dto.request.OrderReservationDTO;
import com.swimming_pool.management.model.dto.response.OrderIdResponseDTO;
import com.swimming_pool.management.model.dto.response.OrderResponseDTO;
import com.swimming_pool.management.model.dto.response.TimeSlotDTO;
import com.swimming_pool.management.model.entity.Order;

import java.util.List;

/**
 * Фасад-сервис для управления записями клиентов на бассейн, используя DTO на основе сущности {@link Order}
 */
public interface OrderFacadeService {

    /**
     * Получение занятых записей на определённую дату
     *
     * @param date дата в виде строки
     * @return список занятых таймслотов с записями {@link TimeSlotDTO} на указанную дату
     */
    List<TimeSlotDTO> getReservedSlotsForDate(String date);

    /**
     * Получение доступных записей на определённую дату
     *
     * @param date дата в виде строки
     * @return список свободных таймслотов для записи {@link TimeSlotDTO} на указанную дату
     */
    List<TimeSlotDTO> getAvailableSlotsForDate(String date);

    /**
     * Получение записей клиента по его ФИО
     *
     * @param clientName ФИО клиента
     * @return объект с данными о записях
     */
    List<OrderResponseDTO> getByClientName(String clientName);

    /**
     * Получение записей по дате посещения
     *
     * @param date дата в виде строки
     * @return объект с данными о записях
     */
    List<OrderResponseDTO> getByDate(String date);

    /**
     * Добавление записи на определённые дату и время
     *
     * @param orderReservationDTO объект с данными для добавления записи
     * @return объект с идентификатором добавленной записи
     */
    OrderIdResponseDTO reserve(OrderReservationDTO orderReservationDTO);

    /**
     * Добавление записей с определённых даты и времени на несколько часов подряд
     *
     * @param multiHourReservationDTO объект с данными для добавления записей на несколько часов подряд
     * @return список объектов с идентификаторами добавленных записей.
     */
    List<OrderIdResponseDTO> reserveForMultiHours(MultiHourReservationDTO multiHourReservationDTO);

    /**
     * Отмена записи клиента на определённые дату и время
     *
     * @param orderCancellationDTO объект с данными о клиенте и записи
     */
    void cancelReservation(OrderCancellationDTO orderCancellationDTO);

}
