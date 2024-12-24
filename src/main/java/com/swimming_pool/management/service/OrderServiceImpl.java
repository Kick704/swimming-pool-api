package com.swimming_pool.management.service;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;
import com.swimming_pool.management.model.entity.Client;
import com.swimming_pool.management.model.entity.Order;
import com.swimming_pool.management.model.enums.OrderStatus;
import com.swimming_pool.management.repository.OrderRepository;
import com.swimming_pool.management.util.DateTimeUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Реализация сервиса для управления сущностью {@link Order}
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Репозиторий для управления записями в БД
     */
    private final OrderRepository orderRepository;

    /**
     * Сервис для управления клиентами
     */
    private final ClientService clientService;

    /**
     * Сервис для управления рабочими часами(графиком)
     */
    private final WorkHourService workHourService;

    /**
     * Конструктор для инициализации {@link OrderRepository}, {@link ClientService} и {@link WorkHourService}
     *
     * @param orderRepository репозиторий для управления записями в БД
     * @param clientService сервис для управления клиентами
     * @param workHourService сервис для управления рабочими часами(графиком)
     */
    public OrderServiceImpl(OrderRepository orderRepository, ClientService clientService,
                            WorkHourService workHourService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.workHourService = workHourService;
    }

    /**
     * Получение записи по его идентификатору
     *
     * @param id идентификатор записи
     * @return запись
     */
    @Override
    @Transactional(readOnly = true)
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new SwimmingPoolManagementException(
                                ErrorCode.NOT_FOUND,
                                "Запись с ID %s не найдена в базе"
                        )
                );
    }

    /**
     * Получение ассоциативного массива, содержащего занятые записями временные слоты на определённую дату.
     *
     * @param date дата для поиска занятых записей
     * @return ассоциативный массив, содержащий пары: время и количество занятых записей на это время, на основе даты
     */
    @Override
    @Transactional(readOnly = true)
    public Map<LocalTime, Integer> getReservedSlotCountsForDate(LocalDate date) {
        validateDateNotInPast(date);
        validateMaxFutureDate(date);
        List<LocalTime> reservedTimesForDate = getReservedTimesForDate(date);
        Map<LocalTime, Integer> reservedSlots = new LinkedHashMap<>();

        for (LocalTime time : reservedTimesForDate) {
            reservedSlots.compute(time, (t, count) -> (count == null) ? 1 : count + 1);
        }

        if (reservedSlots.isEmpty()) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.NOT_FOUND,
                    String.format("Нет занятых записей на %s", DateTimeUtils.formatToDateString(date)));
        }
        return reservedSlots;
    }

    /**
     * Получение ассоциативного массива, содержащего свободные для записей временные слоты на определённую дату.
     *
     * @param date дата для поиска свободных записей
     * @return ассоциативный массив, содержащий пары: время и количество свободных записей на это время, на основе даты
     */
    @Override
    @Transactional(readOnly = true)
    public Map<LocalTime, Integer> getAvailableSlotCountsForDate(LocalDate date) {
        validateDateNotInPast(date);
        validateMaxFutureDate(date);
        List<LocalTime> reservedTimesForDate = getReservedTimesForDate(date);
        List<LocalTime> allHourlyTimeSlots = workHourService.getGeneratedTimeSlotsForDate(date);
        int limitPerHour = workHourService.getLimitPerHour(date);

        Map<LocalTime, Integer> availableSlots = new LinkedHashMap<>();
        allHourlyTimeSlots.forEach(timeSlot -> availableSlots.put(timeSlot, limitPerHour));
        for (LocalTime time : reservedTimesForDate) {
            availableSlots.computeIfPresent(time, (t, count) -> count - 1);
        }

        if (availableSlots.isEmpty()) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.NOT_FOUND,
                    String.format("Нет доступных записей на %s", DateTimeUtils.formatToDateString(date))
            );
        }
        return availableSlots;
    }

    /**
     * Получение списка записей клиента по его ФИО
     *
     * @param clientName ФИО клиента
     * @return список записей клиента по указанному ФИО
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> getByClientName(String clientName) {
        List<Order> orders = orderRepository.findByClientName(clientName);
        if (orders.isEmpty()) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.NOT_FOUND,
                    String.format("Нет записей в базе с ФИО клиента: %s", clientName));
        }
        return orders;
    }

    /**
     * Получение списка записей на определённую дату
     *
     * @param date дата для поиска записей
     * @return список записей на указанную дату
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> getByDate(@NonNull LocalDate date) {
        List<Order> orders = orderRepository.findByDateTimeRange(date.atStartOfDay(), date.atTime(LocalTime.MAX));
        if (orders.isEmpty()) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.NOT_FOUND,
                    String.format("Нет записей в базе на %s", DateTimeUtils.formatToDateString(date))
            );
        }
        return orders;
    }

    /**
     * Добавление записи для клиента на определённые дату и время
     *
     * @param order запись для добавления в систему
     * @param clientId идентификатор клиента
     * @return добавленная запись
     */
    @Override
    @Transactional
    public Order reserve(Order order, Long clientId) {
        checkOrderNotNull(order);
        Client client = clientService.getById(clientId);
        LocalDateTime orderDateTime = order.getDateTime();
        validateDateTimeNotInPastForReserve(orderDateTime);
        validateMaxFutureDateTimeForReserve(orderDateTime);

        if (!workHourService.isWithinWorkHour(orderDateTime)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Дата и время %s для добавления записей находятся вне рабочего графика бассейна",
                            DateTimeUtils.formatToDateTimeString(orderDateTime)
                    )
            );
        }

        validateClientDailyLimit(clientId, orderDateTime.toLocalDate(), 1);
        validateNoReservationsForClientAtDateTime(clientId, orderDateTime);
        validateHourlyLimit(orderDateTime);
        order.setClient(client);
        order.setStatus(OrderStatus.RESERVED);
        return orderRepository.save(order);
    }

    /**
     * Добавление записей для клиента с определённых даты и времени на несколько часов подряд
     *
     * @param order запись для добавления в систему
     * @param clientId идентификатор клиента
     * @param hourCount количество часов для записей
     * @return список добавленных записей
     */
    @Override
    @Transactional
    public List<Order> reserveForMultiHours(Order order, Long clientId, Integer hourCount) {
        checkOrderNotNull(order);
        Client client = clientService.getById(clientId);
        LocalDateTime startDateTime = order.getDateTime();
        LocalDateTime endDateTime = startDateTime.plusHours(hourCount);
        if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Интервал для добавления записей не может пересекать границы одного дня: %s",
                            DateTimeUtils.formatToDateString(startDateTime.toLocalDate())
                    )
            );
        }

        validateDateTimeNotInPastForReserve(startDateTime);
        validateMaxFutureDateTimeForReserve(startDateTime);
        LocalDate orderDate = order.getDateTime().toLocalDate();
        if (!workHourService.isWithinWorkHourRange(orderDate, startDateTime.toLocalTime(), endDateTime.toLocalTime())) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Интервал для добавления записей (с %s по %s) находится вне рабочего графика бассейна",
                            DateTimeUtils.formatToDateTimeString(startDateTime),
                            DateTimeUtils.formatToDateTimeString(endDateTime)
                    )
            );
        }

        validateClientDailyLimit(clientId, orderDate, hourCount);
        List<Order> orders = Stream.iterate(
                        startDateTime,
                        dateTime -> dateTime.isBefore(endDateTime),
                        dateTime -> dateTime.plusHours(1)
                )
                .map(dateTime -> {
                    validateNoReservationsForClientAtDateTime(clientId, dateTime);
                    validateHourlyLimit(dateTime);
                    return Order.Builder.newBuilder()
                            .client(client)
                            .dateTime(dateTime)
                            .status(OrderStatus.RESERVED)
                            .build();
                })
                .toList();
        return orderRepository.saveAll(orders);
    }

    /**
     * Отмена записи по идентификаторам клиента и самой записи
     *
     * @param clientId идентификатор клиента
     * @param orderId идентификатор записи
     */
    @Override
    @Transactional
    public void cancelReservation(Long clientId, Long orderId) {
        clientService.getById(clientId);
        Order order = getById(orderId);
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format("Запись c ID %s уже отменена", orderId)
            );
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    /**
     * Проверка инициализации записи при добавлении в систему
     *
     * @param order проверяемая запись
     */
    private void checkOrderNotNull(Order order) {
        if (order == null) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "Order: передан пустой объект для сохранения"
            );
        }
    }

    /**
     * Валидация верхней границы даты получения занятых или доступных записей
     *
     * @param date проверяемая дата
     */
    // TODO вынести в отдельный сервис
    private void validateMaxFutureDate(@NonNull LocalDate date) {
        LocalDate maxAvailableDate = LocalDate.now().plusMonths(2);
        if (date.isAfter(maxAvailableDate)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Нет занятых или доступных записей на %s, записи возможны только на 2 месяца вперёд: по %s",
                            DateTimeUtils.formatToDateString(date),
                            DateTimeUtils.formatToDateString(maxAvailableDate)
                    )
            );
        }
    }

    /**
     * Валидация даты на то, что она не находится в прошлом, для получения занятых или доступных записей
     *
     * @param date проверяема дата
     */
    // TODO вынести в отдельный сервис
    private void validateDateNotInPast(@NonNull LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Нет занятых или доступных записей на %s, так как это время уже прошло",
                            DateTimeUtils.formatToDateString(date)
                    )
            );
        }
    }

    /**
     * Валидация верхней границы даты с временем для добавления записи
     *
     * @param dateTime проверяемы дата с временем
     */
    // TODO вынести в отдельный сервис
    private void validateMaxFutureDateTimeForReserve(@NonNull LocalDateTime dateTime) {
        LocalDateTime maxAvailableDateTime = LocalDateTime.now().plusMonths(2);
        if (dateTime.isAfter(maxAvailableDateTime)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Нельзя добавить запись на %s, записи доступны только на 2 месяца вперёд: по %s",
                            DateTimeUtils.formatToDateTimeString(dateTime),
                            DateTimeUtils.formatToDateTimeString(maxAvailableDateTime)
                    )
            );
        }
    }

    /**
     * Валидация даты с временем на то, что она не находится в прошлом, для добавления записи
     *
     * @param dateTime проверяемая дата с временем
     */
    // TODO вынести в отдельный сервис
    private void validateDateTimeNotInPastForReserve(@NonNull LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Нельзя добавить запись на %s, так как это время уже прошло",
                            DateTimeUtils.formatToDateTimeString(dateTime)
                    )
            );
        }
    }

    /**
     * Валидация отсутствия занятой записи клиентом по его идентификатору на определённую дату с временем
     *
     * @param clientId идентификатор клиента
     * @param dateTime проверяемые дата с временем
     */
    // TODO вынести в отдельный сервис
    private void validateNoReservationsForClientAtDateTime(Long clientId, LocalDateTime dateTime) {
        if (orderRepository.countReservationsForClientByDateTimeRange(clientId, dateTime, dateTime) > 0) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Запись клиента c ID %s на %s уже существует",
                            clientId,
                            DateTimeUtils.formatToDateTimeString(dateTime)
                    )
            );
        }
    }

    /**
     * Валидация ограничения на количество записей в час по определённой дате с временем
     *
     * @param dateTime дата с временем для добавления записи
     */
    // TODO вынести в отдельный сервис
    private void validateHourlyLimit(LocalDateTime dateTime) {
        Long ordersForDateTime = orderRepository.countReservationsForDateTime(dateTime);
        LocalDate date = dateTime.toLocalDate();
        Integer limitPerHourForDate = workHourService.getLimitPerHour(date);
        if (ordersForDateTime >= limitPerHourForDate) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Лимит записей в час на %s превышен, доступный лимит часов %s",
                            DateTimeUtils.formatToDateString(date),
                            limitPerHourForDate - ordersForDateTime
                    )
            );
        }
    }

    /**
     * Валидация ограничения на количество записей в день на клиента по его идентификатору и количеству часов для записи
     *
     * @param clientId идентификатор клиента
     * @param date дата для добавления записи
     * @param hourCount количество часов для записи
     */
    // TODO вынести в отдельный сервис
    private void validateClientDailyLimit(Long clientId, @NonNull LocalDate date, Integer hourCount) {
        Long clientOrderCountForDate = orderRepository.countReservationsForClientByDateTimeRange(
                clientId,
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)
        );
        Integer dailyLimitPerClientsForDate = workHourService.getDailyLimitPerClients(date);
        if (clientOrderCountForDate + hourCount > dailyLimitPerClientsForDate) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.BAD_REQUEST,
                    String.format(
                            "Лимит записей на указанный день для клиента с ID %s на %s превышен, " +
                                    "доступный лимит часов %s",
                            clientId,
                            DateTimeUtils.formatToDateString(date),
                            dailyLimitPerClientsForDate - clientOrderCountForDate
                    )
            );
        }
    }

    /**
     * Получение списка занятых записями времён на определённую дату
     *
     * @param date дата для извлечения
     * @return список занятых записями времён на указанную дату
     */
    private List<LocalTime> getReservedTimesForDate(@NonNull LocalDate date) {
        List<LocalDateTime> reservedDateTimesForDate = orderRepository.findReservedDateTimesByDateRange(
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)
        );
        return reservedDateTimesForDate.stream()
                .map(LocalDateTime::toLocalTime)
                .collect(Collectors.toList());
    }

}
