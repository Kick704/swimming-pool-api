package com.swimming_pool.management.model.dto.response;

import com.swimming_pool.management.model.entity.Client;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для отображения краткой информации о клиенте на основе сущности {@link Client}
 */
@Schema(description = "Краткие данные о клиенте")
public class ClientSummaryDTO {

    /**
     * Идентификатор клиента
     */
    @Schema(description = "ID клиента", example = "1")
    private Long id;

    /**
     * ФИО клиента
     */
    @Schema(description = "ФИО клиента", example = "Иванов Иван Иванович")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
