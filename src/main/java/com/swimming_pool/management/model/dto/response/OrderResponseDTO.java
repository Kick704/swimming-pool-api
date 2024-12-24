package com.swimming_pool.management.model.dto.response;

import com.swimming_pool.management.model.entity.Order;
import com.swimming_pool.management.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO с информацией о записи на основе сущности {@link Order}
 */
@Schema(description = "Данные о записи")
public class OrderResponseDTO {

    /**
     * Идентификатор клиента
     */
    @Schema(description = "ID клиента", example = "1")
    private Long clientId;

    /**
     * Дата и время посещения бассейна
     */
    @Schema(description = "Дата и время посещения бассейна", example = "21.12.2024 12:00")
    private String datetime;

    /**
     * Идентификатор записи в виде строки
     */
    @Schema(description = "ID записи", example = "1")
    private String orderId;

    /**
     * Описание статуса заказа
     */
    @Schema(description = "Статус записи", example = "RESERVED")
    private OrderStatus status;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
