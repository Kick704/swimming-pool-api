package com.swimming_pool.management.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.time.LocalTime;

/**
 * Сущность рабочих часов(графика) бассейна, неизменяема
 */
@Entity
@Immutable
@Table(name = "work_hours")
public class WorkHour {

    /**
     * Идентификатор сущности
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Начало рабочего дня в часах
     */
    @Column(name = "start_time")
    private LocalTime startTime;

    /**
     * Конец рабочего дня в часах
     */
    @Column(name = "end_time")
    private LocalTime endTime;

    /**
     * Флаг для определения, является ли день праздничным
     */
    @Column(name = "is_holiday")
    private Boolean isHoliday;

    /**
     * Лимит записей в час для рабочего дня
     */
    @Column(name = "limit_per_hour")
    private Integer limitPerHour;

    /**
     * Лимит записей в рабочий день для клиентов
     */
    @Column(name = "daily_limit_per_clients")
    private Integer dailyLimitPerClients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getHoliday() {
        return isHoliday;
    }

    public void setHoliday(Boolean holiday) {
        isHoliday = holiday;
    }

    public Integer getLimitPerHour() {
        return limitPerHour;
    }

    public void setLimitPerHour(Integer limitPerHour) {
        this.limitPerHour = limitPerHour;
    }

    public Integer getDailyLimitPerClients() {
        return dailyLimitPerClients;
    }

    public void setDailyLimitPerClients(Integer dailyLimitPerClients) {
        this.dailyLimitPerClients = dailyLimitPerClients;
    }

    @Override
    public String toString() {
        return "WorkHour{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isHoliday=" + isHoliday +
                ", limitPerHour=" + limitPerHour +
                ", dailyLimitPerClients=" + dailyLimitPerClients +
                '}';
    }

}
