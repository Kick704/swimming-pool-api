package com.swimming_pool.management.facade;

import com.swimming_pool.management.mapper.OrderMapper;
import com.swimming_pool.management.model.dto.request.MultiHourReservationDTO;
import com.swimming_pool.management.model.dto.request.OrderCancellationDTO;
import com.swimming_pool.management.model.dto.request.OrderReservationDTO;
import com.swimming_pool.management.model.dto.response.OrderIdResponseDTO;
import com.swimming_pool.management.model.dto.response.OrderResponseDTO;
import com.swimming_pool.management.model.dto.response.TimeSlotDTO;
import com.swimming_pool.management.model.entity.Order;
import com.swimming_pool.management.service.OrderService;
import com.swimming_pool.management.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Реализация фасад-сервиса для управления записями клиентов на бассейн, используя DTO на основе сущности {@link Order}
 */
@Service
public class OrderFacadeServiceImpl implements OrderFacadeService {

    /**
     * Сервис для работы с сущностью записи
     */
    private final OrderService orderService;

    /**
     * Маппер для преобразования между DTO и сущностью записи
     */
    private final OrderMapper orderMapper;

    /**
     * Конструктор для инициализации {@link OrderService} и {@link OrderMapper}
     *
     * @param orderService сервис для работы с сущностью записи
     * @param orderMapper маппер для преобразования между DTO и сущностью записи
     */
    public OrderFacadeServiceImpl(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    /**
     * Получение занятых записей на определённую дату
     *
     * @param date дата в виде строки
     * @return список занятых таймслотов с записями {@link TimeSlotDTO} на указанную дату
     */
    @Override
    public List<TimeSlotDTO> getReservedSlotsForDate(String date) {
        Map<LocalTime, Integer> reservedSlotsForDate = orderService.getReservedSlotCountsForDate(
                DateTimeUtils.parseToLocalDate(date)
        );
        return orderMapper.toTimeSlotDTOList(reservedSlotsForDate);
    }

    /**
     * Получение доступных записей на определённую дату
     *
     * @param date дата в виде строки
     * @return список свободных таймслотов для записи {@link TimeSlotDTO} на указанную дату
     */
    @Override
    public List<TimeSlotDTO> getAvailableSlotsForDate(String date) {
        Map<LocalTime, Integer> availableSlotsForDate = orderService.getAvailableSlotCountsForDate(
                DateTimeUtils.parseToLocalDate(date)
        );
        return orderMapper.toTimeSlotDTOList(availableSlotsForDate);
    }

    /**
     * Получение записей клиента по его ФИО
     *
     * @param clientName ФИО клиента
     * @return объект с данными о записях
     */
    @Override
    public List<OrderResponseDTO> getByClientName(String clientName) {
        List<Order> orders = orderService.getByClientName(clientName);
        return orderMapper.toOrderResponseDTOList(orders);
    }

    /**
     * Получение записей по дате посещения
     *
     * @param date дата в виде строки
     * @return объект с данными о записях
     */
    @Override
    public List<OrderResponseDTO> getByDate(String date) {
        List<Order> orders = orderService.getByDate(DateTimeUtils.parseToLocalDate(date));
        return orderMapper.toOrderResponseDTOList(orders);
    }

    /**
     * Добавление записи на определённые дату и время
     *
     * @param orderReservationDTO объект с данными для добавления записи
     * @return объект с идентификатором добавленной записи
     */
    @Override
    public OrderIdResponseDTO reserve(OrderReservationDTO orderReservationDTO) {
        Long clientId = orderReservationDTO.getClientId();
        Order newOrder = orderMapper.toEntity(orderReservationDTO);
        Order createdOrder = orderService.reserve(newOrder, clientId);
        return orderMapper.toOrderIdResponseDTO(createdOrder);
    }

    /**
     * Добавление записей с определённых даты и времени на несколько часов подряд
     *
     * @param multiHourReservationDTO объект с данными для добавления записей на несколько часов подряд
     * @return список объектов с идентификаторами добавленных записей.
     */
    @Override
    public List<OrderIdResponseDTO> reserveForMultiHours(MultiHourReservationDTO multiHourReservationDTO) {
        Long clientId = multiHourReservationDTO.getClientId();
        Integer hourCount = multiHourReservationDTO.getHourCount();
        Order newOrder = orderMapper.toEntity(multiHourReservationDTO);
        List<Order> createdOrders = orderService.reserveForMultiHours(newOrder, clientId, hourCount);
        return orderMapper.toOrderIdResponseDTOList(createdOrders);
    }

    /**
     * Отмена записи клиента на определённые дату и время
     *
     * @param orderCancellationDTO объект с данными о клиенте и записи
     */
    @Override
    public void cancelReservation(OrderCancellationDTO orderCancellationDTO) {
        Long clientId = orderCancellationDTO.getClientId();
        Long orderId = Long.valueOf(orderCancellationDTO.getOrderId());
        orderService.cancelReservation(clientId, orderId);
    }

}
