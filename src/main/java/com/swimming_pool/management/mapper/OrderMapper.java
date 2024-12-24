package com.swimming_pool.management.mapper;

import com.swimming_pool.management.model.dto.request.MultiHourReservationDTO;
import com.swimming_pool.management.model.dto.request.OrderReservationDTO;
import com.swimming_pool.management.model.dto.response.OrderIdResponseDTO;
import com.swimming_pool.management.model.dto.response.OrderResponseDTO;
import com.swimming_pool.management.model.dto.response.TimeSlotDTO;
import com.swimming_pool.management.model.entity.Order;
import com.swimming_pool.management.util.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Маппер для преобразований между сущностью {@link Order} и связанных с ним DTO
 */
@Mapper(componentModel = "spring", imports = DateTimeUtils.class)
public interface OrderMapper {

    /**
     * Маппинг из сущности в DTO с ID записи
     *
     * @param order сущность записи
     * @return DTO с ID записи
     */
    @Mapping(target = "orderId", source = "order.id")
    OrderIdResponseDTO toOrderIdResponseDTO(Order order);

    /**
     * Маппинг из списка сущностей в список DTO с ID записи
     *
     * @param orders список сущностей записей
     * @return список DTO с ID записи
     */
    List<OrderIdResponseDTO> toOrderIdResponseDTOList(List<Order> orders);

    /**
     * Маппинг из сущности в DTO со всеми данными о записи
     *
     * @param order сущность записи
     * @return DTO со всеми данными о записи
     */
    @Mapping(target = "clientId", expression = "java(order.getClient().getId())")
    @Mapping(target = "datetime",
            expression = "java(DateTimeUtils.formatToDateTimeString(order.getDateTime()))")
    @Mapping(target = "orderId", source = "order.id")
    OrderResponseDTO toOrderResponseDTO(Order order);

    /**
     * Маппинг из списка сущностей в список DTO со всеми данными о записи
     *
     * @param order список сущностей записей
     * @return список DTO со всеми данными о записи
     */
    List<OrderResponseDTO> toOrderResponseDTOList(List<Order> order);

    /**
     * Маппинг в DTO таймслота на основе входных данных
     *
     * @param time время
     * @param count количество записей на указанное время
     * @return DTO таймслота
     */
    @Mapping(target = "time", source = "time")
    @Mapping(target = "count", source = "count")
    TimeSlotDTO toTimeSlotDTO(String time, int count);

    /**
     * Маппинг в список DTO таймслотов на основе ассоциативного массива, содержащего временные слоты.
     * <p>Дефолтный метод, собирающий список через маппинг каждого элемента
     *
     * @param timeSlots ассоциативный массив, содержащий пары: время и количество записей на это время
     * @return список DTO таймслотов
     */
    default List<TimeSlotDTO> toTimeSlotDTOList(Map<LocalTime, Integer> timeSlots) {
        return timeSlots.entrySet().stream()
                .map(entry -> {
                            String time = DateTimeUtils.formatToTimeString(entry.getKey());
                            return toTimeSlotDTO(time, entry.getValue());
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     * Маппинг DTO для добавления записи в сущность
     *
     * @param orderReservationDTO DTO для добавления записи
     * @return сущность клиента
     */
    @Mapping(target = "dateTime",
            expression = "java(DateTimeUtils.parseToLocalDateTime(orderReservationDTO.getDatetime()))")
    Order toEntity(OrderReservationDTO orderReservationDTO);

    /**
     * Маппинг DTO добавления записей на несколько часов подряд в сущность
     *
     * @param multiHourReservationDTO DTO для добавления записей на несколько часов подряд
     * @return сущность клиента
     */
    @Mapping(target = "dateTime",
            expression = "java(DateTimeUtils.parseToLocalDateTime(multiHourReservationDTO.getInitialDatetime()))")
    Order toEntity(MultiHourReservationDTO multiHourReservationDTO);

}
