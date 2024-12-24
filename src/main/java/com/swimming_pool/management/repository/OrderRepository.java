package com.swimming_pool.management.repository;

import com.swimming_pool.management.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностью {@link Order} между приложением и БД
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Выборка записи по его идентификатору
     *
     * @param id идентификатор записи
     * @return {@link Optional} - контейнер, который может содержать клиента {@link Order} по указанному идентификатору
     */
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findById(@Param("id") Long id);

    /**
     * Выборка всех дат с временами занятых записей по интервалу
     *
     * @param startDateTime дата с временем, с которой будет выполняться поиск
     * @param endDateTime дата с временем, по которую будет выполняться поиск
     * @return список дат с временами занятых записей по указанному интервалу дат с временами
     */
    @Query("SELECT o.dateTime FROM Order o WHERE o.status = 'RESERVED' " +
            "AND o.dateTime BETWEEN :startDateTime AND :endDateTime")
    List<LocalDateTime> findReservedDateTimesByDateRange(@Param("startDateTime") LocalDateTime startDateTime,
                                                         @Param("endDateTime") LocalDateTime endDateTime);

    /**
     * Выборка всех записей по интервалу дату с временами
     *
     * @param startDateTime дата с временем, с которой будет выполняться поиск
     * @param endDateTime дата с временем, по которую будет выполняться поиск
     * @return список записей по указанному интервалу дат
     */
    @Query("SELECT o FROM Order o WHERE o.dateTime BETWEEN :startDateTime AND :endDateTime")
    List<Order> findByDateTimeRange(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime);

    /**
     * Выборка всех записей клиента по его ФИО
     * @param clientName ФИО клиента
     * @return список записей клиента по указанному ФИО
     */
    @Query("SELECT o FROM Order o WHERE o.client.name = :clientName")
    List<Order> findByClientName(@Param("clientName") String clientName);

    /**
     * Выборка количества занятых записей по дате с временем
     *
     * @param datetime дата с временем, на которую будет выполняться поиск
     * @return количество занятых записей на указанную дату с временем
     */
    @Query("SELECT COUNT(o) FROM Order o WHERE o.dateTime = :dateTime AND o.status = 'RESERVED'")
    Long countReservationsForDateTime(@Param("dateTime") LocalDateTime datetime);

    /**
     * Выборка количества занятых клиентом записей по его идентификатору и по интервалу дату с временами
     *
     * @param clientId идентификатор клиента
     * @param startDateTime дата с временем, с которой будет выполняться поиск
     * @param endDateTime дата с временем, по которую будет выполняться поиск
     * @return количество занятых записей по указанным идентификатору клиента и интервалу дату с временами
     */
    @Query("SELECT COUNT(o) FROM Order o WHERE o.client.id = :clientId AND o.status = 'RESERVED' " +
            "AND o.dateTime BETWEEN :startDateTime AND :endDateTime")
    Long countReservationsForClientByDateTimeRange(@Param("clientId") Long clientId,
                                                   @Param("startDateTime") LocalDateTime startDateTime,
                                                   @Param("endDateTime") LocalDateTime endDateTime);

}
