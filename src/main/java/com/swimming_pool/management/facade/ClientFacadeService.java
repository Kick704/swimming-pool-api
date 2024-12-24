package com.swimming_pool.management.facade;

import com.swimming_pool.management.model.dto.request.ClientCreationDTO;
import com.swimming_pool.management.model.dto.request.ClientUpdateDTO;
import com.swimming_pool.management.model.dto.response.ClientDetailsDTO;
import com.swimming_pool.management.model.dto.response.ClientSummaryDTO;
import com.swimming_pool.management.model.entity.Client;

import java.util.List;

/**
 * Фасад-сервис для управления клиентами бассейна, используя DTO на основе сущности {@link Client}
 */
public interface ClientFacadeService {

    /**
     * Получение детальной информации о клиенте по его ID
     *
     * @param id идентификатор клиента
     * @return {@link ClientDetailsDTO} — объект с детальной информацией о клиенте
     */
    ClientDetailsDTO getById(Long id);

    /**
     * Получение списка всех клиентов бассейна
     *
     * @return список объектов {@link ClientSummaryDTO}, содержащих краткую информацию о клиентах
     */
    List<ClientSummaryDTO> getAll();

    /**
     * Добавление нового клиента
     *
     * @param clientCreationDTO объект, содержащий данные для создания нового клиента
     */
    void addNew(ClientCreationDTO clientCreationDTO);

    /**
     * Обновление данных существующего клиента
     *
     * @param clientUpdateDTO объект, содержащий обновленные данные о клиенте
     */
    void update(ClientUpdateDTO clientUpdateDTO);


}
