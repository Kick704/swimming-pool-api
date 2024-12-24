package com.swimming_pool.management.repository;

import com.swimming_pool.management.model.entity.WorkHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностью {@link WorkHour} между приложением и БД
 */
@Repository
public interface WorkHourRepository extends JpaRepository<WorkHour, Long> {

    /**
     * Выборка рабочих часов(графика) на основе флага для определения, является ли день праздничным
     *
     * @param isHoliday флаг для определения, является ли день праздничным
     * @return {@link Optional} - контейнер, который может содержать повседневный или праздничный график
     */
    @Query("SELECT wh FROM WorkHour wh WHERE wh.isHoliday = :isHoliday")
    Optional<WorkHour> findWorkHour(@Param("isHoliday") Boolean isHoliday);

}
