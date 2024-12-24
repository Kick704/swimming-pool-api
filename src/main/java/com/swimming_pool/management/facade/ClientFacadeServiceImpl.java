package com.swimming_pool.management.facade;

import com.swimming_pool.management.mapper.ClientMapper;
import com.swimming_pool.management.model.dto.request.ClientCreationDTO;
import com.swimming_pool.management.model.dto.request.ClientUpdateDTO;
import com.swimming_pool.management.model.dto.response.ClientDetailsDTO;
import com.swimming_pool.management.model.dto.response.ClientSummaryDTO;
import com.swimming_pool.management.model.entity.Client;
import com.swimming_pool.management.service.ClientService;
import com.swimming_pool.management.util.ClientDataUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация фасад-сервис для управления клиентами бассейна с использованием DTO на основе сущности {@link Client}
 */
@Service
public class ClientFacadeServiceImpl implements ClientFacadeService {

    /**
     * Сервис для работы с сущностью клиента
     */
    private final ClientService clientService;

    /**
     * Маппер для преобразования между DTO и сущностью клиента
     */
    private final ClientMapper clientMapper;

    /**
     * Конструктор для инициализации {@link ClientService} и {@link ClientMapper}.
     *
     * @param clientService сервис для работы с сущностью клиента
     * @param clientMapper маппер для преобразования между DTO и сущностью клиента
     */
    public ClientFacadeServiceImpl(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    /**
     * Получение детальной информации о клиенте по его ID
     *
     * @param id идентификатор клиента
     * @return {@link ClientDetailsDTO} — объект с детальной информацией о клиенте
     */
    @Override
    public ClientDetailsDTO getById(Long id) {
        return clientMapper.toDetailsDTO(clientService.getById(id));
    }

    /**
     * Получение списка всех клиентов бассейна
     *
     * @return список объектов {@link ClientSummaryDTO}, содержащих краткую информацию о клиентах
     */
    @Override
    public List<ClientSummaryDTO> getAll() {
        return clientMapper.toSummaryDTOList(clientService.getAll());
    }

    /**
     * Добавление нового клиента с валидацией номера телефона и email
     *
     * @param clientCreationDTO объект, содержащий данные для создания нового клиента
     */
    @Override
    public void addNew(@NonNull ClientCreationDTO clientCreationDTO) {
        Client newClient = clientMapper.toEntity(clientCreationDTO);
        clientService.validatePhoneUniqueness(newClient.getPhone());
        clientService.validateEmailUniqueness(newClient.getEmail());
        clientService.save(newClient);
    }

    /**
     * Обновление данных существующего клиента валидацией номера телефона и email
     *
     * @param clientUpdateDTO объект, содержащий обновленные данные о клиенте
     */
    @Override
    public void update(@NonNull ClientUpdateDTO clientUpdateDTO) {
        Client client = clientService.getById(clientUpdateDTO.getId());
        String updatedPhone = clientUpdateDTO.getPhone();
        String updatedEmail = clientUpdateDTO.getEmail();
        if (!client.getPhone().equals(updatedPhone)) {
            clientService.validatePhoneUniqueness(ClientDataUtils.formatPhoneForEntity(updatedPhone));
        }
        if (!client.getEmail().equals(updatedEmail)) {
            clientService.validateEmailUniqueness(updatedEmail);
        }
        clientMapper.updateEntityFromDto(clientUpdateDTO, client);
        clientService.save(client);
    }

}
