package com.swimming_pool.management.controller.v0;

import com.swimming_pool.management.facade.ClientFacadeService;
import com.swimming_pool.management.model.dto.request.ClientCreationDTO;
import com.swimming_pool.management.model.dto.request.ClientUpdateDTO;
import com.swimming_pool.management.model.dto.response.ClientDetailsDTO;
import com.swimming_pool.management.model.dto.response.ClientSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST-контроллер для управления клиентами бассейна
 */
@RestController
@Validated
@RequestMapping("${api-base-path}/client")
@Tag(name = "Клиенты", description = "Управление клиентами бассейна")
public class ClientController {

    /**
     * Фасад-сервис для работы с DTO клиентов
     */
    private final ClientFacadeService clientFacadeService;

    /**
     * Конструктор для инициализации {@link ClientFacadeService}
     *
     * @param clientFacadeService фасад-сервис для работы с DTO клиентов
     */
    public ClientController(ClientFacadeService clientFacadeService) {
        this.clientFacadeService = clientFacadeService;
    }

    /**
     * Обработчик GET запроса для получения данных о клиенте
     *
     * @param id идентификатор клиента
     * @return {@link ClientDetailsDTO} — объект с детальной информацией о клиенте
     */
    @GetMapping("/get")
    @Operation(summary = "Получение данных о клиенте", description = "Позволяет получить данные о клиенте по его ID")
    public ClientDetailsDTO getClient(@RequestParam(value = "id")
                                      @NotNull(message = "Не введён ID клиента")
                                      Long id) {
        return clientFacadeService.getById(id);
    }

    /**
     * Обработчик GET запроса для получения списка клиентов бассейна
     *
     * @return список с краткой информацией о клинтах {@link ClientSummaryDTO}
     */
    @GetMapping("/all")
    @Operation(summary = "Получение списка клиентов бассейна",
            description = "Позволяет получить список всех клиентов бассейна")
    public List<ClientSummaryDTO> getClients() {
        return clientFacadeService.getAll();
    }

    /**
     * Обработчик POST запроса для добавления нового клиента
     *
     * @param clientCreationDTO объект, содержащий данные для создания нового клиента
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление нового клиента",
            description = "Позволяет добавить нового клиента для записи в бассейн")
    public void addClient(@Valid @RequestBody ClientCreationDTO clientCreationDTO) {
        clientFacadeService.addNew(clientCreationDTO);
    }

    /**
     * Обработчик PUT запроса для обновления данных о клиенте
     *
     * @param clientUpdateDTO объект, содержащий обновленные данные о клиенте
     */
    @PutMapping("/update")
    @Operation(summary = "Обновление данных о клиенте",
            description = "Позволяет изменить данные о существующем в базе клиенте")
    public void updateClient(@Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
        clientFacadeService.update(clientUpdateDTO);
    }

}
