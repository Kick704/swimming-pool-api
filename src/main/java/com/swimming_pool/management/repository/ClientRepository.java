package com.swimming_pool.management.repository;

import com.swimming_pool.management.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностью {@link Client} между приложением и БД
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Выборка клиента по его идентификатору
     *
     * @param id идентификатор клиента
     * @return {@link Optional} - контейнер, который может содержать клиента {@link Client} по указанному идентификатору
     */
    @Query(value = "SELECT c FROM Client c WHERE c.id = :id")
    Optional<Client> findById(@Param("id") Long id);

    /**
     * Выборка всех клиентов
     *
     * @return список, содержащий список всех пользователей {@link Client} в БД
     */
    @Query(value = "SELECT c FROM Client c")
    List<Client> findAll();

    /**
     * Проверка существования клиента в БД с указанным номером телефона
     *
     * @param phone номер телефона клиента
     * @return результат проверки
     */
    boolean existsByPhone(String phone);

    /**
     * Проверка существования клиента в БД с указанным email
     *
     * @param email электронная почта клиента
     * @return результат проверки
     */
    boolean existsByEmail(String email);

}
