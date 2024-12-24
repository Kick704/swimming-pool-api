package com.swimming_pool.management.service;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;
import com.swimming_pool.management.model.entity.Client;
import com.swimming_pool.management.repository.ClientRepository;
import com.swimming_pool.management.util.ClientDataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервис для управления сущностью {@link Client}
 */
@Service
public class ClientServiceImpl implements ClientService {

    /**
     * Репозиторий для управления клиентами в БД
     */
    private final ClientRepository clientRepository;

    /**
     * Конструктор для инициализации {@link ClientRepository}
     * @param clientRepository репозиторий для управления клиентами в БД
     */
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Получение клиента по его идентификатору
     *
     * @param id идентификатор клиента
     * @return клиент
     */
    @Override
    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new SwimmingPoolManagementException(
                                ErrorCode.NOT_FOUND,
                                String.format("Клиент с ID %s не найден в базе", id)
                        )
                );
    }

    /**
     * Получение всех списка клиентов
     *
     * @return список всех клиентов
     */
    @Override
    @Transactional(readOnly = true)
    public List<Client> getAll() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new SwimmingPoolManagementException(ErrorCode.NOT_FOUND, "Нет клиентов в базе");
        }
        return clients;
    }

    /**
     * Сохранение клиента в системе
     *
     * @param client клиент для сохранения
     */
    @Override
    @Transactional
    public void save(Client client) {
        if (client == null) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    "Client: передан пустой объект для сохранения"
            );
        }
        clientRepository.save(client);
    }

    /**
     * Проверка номера телефона клиента на уникальность в БД
     *
     * @param phone номер телефона клиента
     */
    @Override
    @Transactional(readOnly = true)
    // TODO вынести в отдельный сервис
    public void validatePhoneUniqueness(String phone) {
        if (clientRepository.existsByPhone(phone)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.CONFLICT,
                    String.format(
                            "Клиент с номером телефона %s уже существует в базе",
                            ClientDataUtils.formatPhoneForDTO(phone)
                    )
            );
        }
    }

    /**
     * Проверка email клиента на уникальность в БД
     *
     * @param email электронная почта клиента
     */
    @Override
    @Transactional(readOnly = true)
    // TODO вынести в отдельный сервис
    public void validateEmailUniqueness(String email) {
        if (clientRepository.existsByEmail(email)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.CONFLICT,
                    String.format("Клиент с email %s уже существует в базе", email)
            );
        }
    }

}
