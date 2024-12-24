package com.swimming_pool.management.model.enums;

/**
 * Перечисление статусов записей
 */
public enum OrderStatus {

    RESERVED("Добавлена"),
    CANCELLED("Отменена");

    /**
     * Описание статуса
     */
    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
