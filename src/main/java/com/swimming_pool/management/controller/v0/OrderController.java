package com.swimming_pool.management.controller.v0;

import com.swimming_pool.management.facade.OrderFacadeService;
import com.swimming_pool.management.model.dto.request.MultiHourReservationDTO;
import com.swimming_pool.management.model.dto.request.OrderCancellationDTO;
import com.swimming_pool.management.model.dto.request.OrderReservationDTO;
import com.swimming_pool.management.model.dto.response.OrderIdResponseDTO;
import com.swimming_pool.management.model.dto.response.OrderResponseDTO;
import com.swimming_pool.management.model.dto.response.TimeSlotDTO;
import com.swimming_pool.management.util.DateTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для управления бронированием времени в бассейне
 */
@RestController
@Validated
@RequestMapping("${api-base-path}/timetable")
@Tag(name = "Записи клиентов", description = "Управление бронированием времени в бассейне")
public class OrderController {

    /**
     * Фасад-сервис для работы с DTO записей клиентов
     */
    private final OrderFacadeService orderFacadeService;

    /**
     * Конструктор для инициализации {@link OrderFacadeService}
     *
     * @param orderFacadeService фасад-сервис для работы с DTO записей клиентов
     */
    public OrderController(OrderFacadeService orderFacadeService) {
        this.orderFacadeService = orderFacadeService;
    }

    /**
     * Обработчик GET запроса для получения занятых записей на определённую дату
     *
     * @param date дата в виде строки
     * @return список занятых таймслотов с записями {@link TimeSlotDTO} на указанную дату
     */
    @GetMapping("/all")
    @Operation(summary = "Получение занятых записей на определённую дату",
            description = "Предоставляет список занятых таймслотов с записями на указанную дату")
    public List<TimeSlotDTO> getAll(@RequestParam(value = "date")
                                    @Pattern(regexp = DateTimeUtils.DATE_REGEXP,
                                            message = "Дата должна быть в формате ДД.ММ.ГГГГ")
                                    String date) {
        return orderFacadeService.getReservedSlotsForDate(date);
    }

    /**
     * Обработчик GET запроса для получения доступных записей на определённую дату
     *
     * @param date дата в виде строки
     * @return список свободных таймслотов для записи {@link TimeSlotDTO} на указанную дату
     */
    @GetMapping("/available")
    @Operation(summary = "Получение доступных записей на определённую дату",
            description = "Предоставляет список свободных таймслотов для записи на указанную дату")
    public List<TimeSlotDTO> getAvailable(@RequestParam(value = "date")
                                          @Pattern(regexp = DateTimeUtils.DATE_REGEXP,
                                                  message = "Дата должна быть в формате ДД.ММ.ГГГГ")
                                          String date) {
        return orderFacadeService.getAvailableSlotsForDate(date);
    }

    /**
     * Обработчик GET запроса для получения записей клиента по его ФИО
     *
     * @param clientName ФИО клиента
     * @return объект с данными о записях
     */
    @GetMapping("/by-client-name")
    @Operation(summary = "Получение записей клиента по его ФИО",
            description = "Предоставляет список записей клиента по его ФИО")
    public List<OrderResponseDTO> getByClientName(@RequestParam(value = "clientName")
                                                  @NotBlank(message = "Не введены ФИО клиента")
                                                  @Size(min = 2, max = 100,
                                                          message = "Длина ФИО должна быть от 2 до 100 символов")
                                                  String clientName) {
        return orderFacadeService.getByClientName(clientName);
    }

    /**
     * Обработчик GET запроса для получения записей по дате посещения
     *
     * @param date дата в виде строки
     * @return объект с данными о записях
     */
    @GetMapping("/by-date")
    @Operation(summary = "Получение записей по дате посещения",
            description = "Предоставляет список всех записей на указанную дату")
    public List<OrderResponseDTO> getByDate(@RequestParam(value = "date")
                                            @Pattern(regexp = DateTimeUtils.DATE_REGEXP,
                                                    message = "Дата должна быть в формате ДД.ММ.ГГГГ")
                                            String date) {
        return orderFacadeService.getByDate(date);
    }

    /**
     * Обработчик POST запроса для добавления записи клиента на определённые дату и время
     *
     * @param orderReservationDTO объект с данными для добавления записи
     * @return объект с идентификатором добавленной записи
     */
    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить запись клиента на определённые дату и время",
            description = "Позволяет записать клиента на посещение бассейна в указанные дату и время")
    public OrderIdResponseDTO reserve(@Valid @RequestBody OrderReservationDTO orderReservationDTO) {
        return orderFacadeService.reserve(orderReservationDTO);
    }

    /**
     * Обработчик POST запроса для добавления записей клиента на определённые дату и время на несколько часов подряд
     *
     * @param multiHourReservationDTO объект с данными для добавления записей на несколько часов подряд
     * @return список объектов с идентификаторами добавленных записей.
     */
    @PostMapping("/reserve/multi-hour")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить записи клиента на определённые дату и время на несколько часов подряд",
            description = " Позволяет записать клиента на посещение бассейна в указанные дату и время " +
                    "на несколько часов подряд")
    public List<OrderIdResponseDTO> reserveForMultiHours(
            @Valid @RequestBody MultiHourReservationDTO multiHourReservationDTO) {
        return orderFacadeService.reserveForMultiHours(multiHourReservationDTO);
    }

    /**
     * Обработчик PUT запроса для отмены записи клиента на определённые дату и время
     *
     * @param orderCancellationDTO объект с данными о клиенте и записи
     */
    @PutMapping("/cancel")
    @Operation(summary = "Отмена записи клиента на определённые дату и время",
            description = "Позволяет отменить запись клиента ")
    public void cancel(@Valid @RequestBody OrderCancellationDTO orderCancellationDTO) {
        orderFacadeService.cancelReservation(orderCancellationDTO);
    }

}
