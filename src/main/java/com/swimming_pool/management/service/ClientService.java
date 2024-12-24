package com.swimming_pool.management.service;

import com.swimming_pool.management.model.entity.Client;

import java.util.List;

/**
 * Сервис для управления сущностью {@link Client}
 */
public interface ClientService {

    /**
     * Получение клиента по его идентификатору
     *
     * @param id идентификатор клиента
     * @return клиент
     */
    Client getById(Long id);

    /**
     * Получение всех списка клиентов
     *
     * @return список всех клиентов
     */
    List<Client> getAll();

    /**
     * Сохранение клиента в системе
     *
     * @param client клиент для сохранения
     */
    void save(Client client);

    /**
     * Проверка номера телефона клиента на уникальность в системе
     *
     * @param phone проверяемый номер телефона
     */
    // TODO вынести в отдельный сервис
    void validatePhoneUniqueness(String phone);

    /**
     * Проверка email клиента на уникальность в системе
     *
     * @param email проверяемый email
     */
    // TODO вынести в отдельный сервис
    void validateEmailUniqueness(String email);

}
