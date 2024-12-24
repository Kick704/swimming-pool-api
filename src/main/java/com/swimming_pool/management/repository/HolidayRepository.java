package com.swimming_pool.management.repository;

import com.swimming_pool.management.model.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Репозиторий для управления сущностью {@link Holiday} между приложением и БД
 */
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    /**
     * Выборка праздничного дня по дате
     *
     * @param date дата
     * @return {@link Optional} - контейнер, который может содержать клиента {@link Holiday} по указанной дате
     */
    @Query("SELECT h FROM Holiday h WHERE h.date = :date")
    Optional<Holiday> findByDate(@Param("date")LocalDate date);

}
