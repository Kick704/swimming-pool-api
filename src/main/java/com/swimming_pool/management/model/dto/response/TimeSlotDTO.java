package com.swimming_pool.management.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO c информацией о времени и количеством занятых/свободных записей на это время
 */
@Schema(description = "Таймслот с количеством занятых/свободных записей")
public class TimeSlotDTO {

    @Schema(description = "Время")
    private String time;

    @Schema(description = "Количество занятых/свободных записей")
    private Integer count;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
