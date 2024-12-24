package com.swimming_pool.management.model.dto.response;

import com.swimming_pool.management.model.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO с идентификатором записи на основе сущности {@link Order}
 */
@Schema(description = "Идентификатор записи")
public class OrderIdResponseDTO {

    /**
     * Идентификатор записи в виде строки
     */
    @Schema(description = "ID записи", example = "1")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
