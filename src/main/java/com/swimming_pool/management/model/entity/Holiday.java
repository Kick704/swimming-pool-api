package com.swimming_pool.management.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

/**
 * Сущность праздничного дня
 */
@Entity
@Immutable
@Table(name = "holidays")
public class Holiday {

    /**
     * Идентификатор сущности
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Дата праздничного дня
     */
    @Column(name = "date")
    private LocalDate date;

    /**
     * Описание праздничного дня
     */
    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
