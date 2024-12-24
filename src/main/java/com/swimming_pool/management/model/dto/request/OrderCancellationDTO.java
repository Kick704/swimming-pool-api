package com.swimming_pool.management.model.dto.request;

import com.swimming_pool.management.model.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для отмены существующей записи {@link Order}
 */
@Schema(description = "Данные для отмены записи клиента")
public class OrderCancellationDTO {

    /**
     * Идентификатор клиента
     */
    @NotNull(message = "Не введён ID клиента")
    @Schema(description = "ID клиента", example = "1")
    private Long clientId;

    /**
     * Идентификатор клиента в виде строки
     */
    @NotBlank(message = "Не введён ID записи")
    @Schema(description = "ID записи", example = "1")
    private String orderId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
