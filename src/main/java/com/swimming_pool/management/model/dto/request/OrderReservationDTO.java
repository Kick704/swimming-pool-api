package com.swimming_pool.management.model.dto.request;

import com.swimming_pool.management.model.entity.Order;
import com.swimming_pool.management.util.DateTimeUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO для создания сущности записи {@link Order}
 */
@Schema(description = "Данные для добавления записи на определённые дату и время")
public class OrderReservationDTO {

    /**
     * Идентификатор клиента
     */
    @NotNull(message = "Не введён ID клиента")
    @Schema(description = "ID клиента", example = "1")
    private Long clientId;

    /**
     * Дата и время посещения бассейна
     */
    @NotBlank(message = "Не введены дата и время посещения бассейна")
    @Pattern(regexp = DateTimeUtils.DATETIME_REGEXP, message = "Некорректно введены дата и время посещения бассейна")
    @Schema(description = "Дата и время посещения бассейна", example = "21.12.2024 12:00")
    private String datetime;

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

}
