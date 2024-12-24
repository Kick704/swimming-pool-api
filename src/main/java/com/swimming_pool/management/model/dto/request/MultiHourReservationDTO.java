package com.swimming_pool.management.model.dto.request;

import com.swimming_pool.management.model.entity.Order;
import com.swimming_pool.management.util.DateTimeUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO для создания сущностей записей {@link Order} на основе указанного количество часов
 */
@Schema(description = "Данные для добавления записей на несколько часов подряд")
public class MultiHourReservationDTO {

    /**
     * Идентификатор клиента
     */
    @NotNull(message = "Не введён ID клиента")
    @Schema(description = "ID клиента", example = "1")
    private Long clientId;

    /**
     * Дата и время начала записи на посещение бассейна
     */
    @NotBlank(message = "Не введены дата и время начала записи")
    @Pattern(regexp = DateTimeUtils.DATETIME_REGEXP, message = "Некорректные дата и время начала записи")
    @Schema(description = "Дата и время начала записи на посещение бассейна", example = "21.12.2024 12:00")
    private String initialDatetime;

    /**
     * Количество часов для записей
     */
    @NotNull(message = "Не введено количество часов для записей")
    @Min(value = 1, message = "Количество часов для записей должно быть от 1 до 6")
    @Max(value = 6, message = "Количество часов для записей должно быть от 1 до 6")
    @Schema(description = "Количество часов для записей", example = "2")
    private Integer hourCount;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getInitialDatetime() {
        return initialDatetime;
    }

    public void setInitialDatetime(String initialDatetime) {
        this.initialDatetime = initialDatetime;
    }

    public Integer getHourCount() {
        return hourCount;
    }

    public void setHourCount(Integer hourCount) {
        this.hourCount = hourCount;
    }

}
