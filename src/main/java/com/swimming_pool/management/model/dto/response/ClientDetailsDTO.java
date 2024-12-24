package com.swimming_pool.management.model.dto.response;

import com.swimming_pool.management.model.entity.Client;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для отображения детальной информации о клиенте на основе сущности {@link Client}
 */
@Schema(description = "Детальные данные о клиенте")
public class ClientDetailsDTO {

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

    /**
     * Номер телефона клиента
     */
    @Schema(description = "Номер телефона клиента", example = "+79378901234")
    private String phone;

    /**
     * Email клиента
     */
    @Schema(description = "Email клиента", example = "ivanov@mail.com")
    private String email;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
