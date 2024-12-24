package com.swimming_pool.management.model.dto.request;

import com.swimming_pool.management.model.entity.Client;
import com.swimming_pool.management.util.ClientDataUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO для обновления сущности клиента {@link Client}
 */
@Schema(description = "Данные для обновления клиента")
public class ClientUpdateDTO {

    /**
     * Идентификатор клиента
     */
    @NotNull(message = "Не введён ID")
    @Schema(description = "ID клиента", example = "1")
    private Long id;

    /**
     * ФИО клиента
     */
    @Size(min = 2, max = 100, message = "Длина ФИО должна быть от 2 до 100 символов")
    @Schema(description = "ФИО клиента", example = "Иванов Иван Иванович")
    private String name;

    /**
     * Номер телефона клиента
     */
    @Pattern(regexp = ClientDataUtils.DTO_PHONE_REGEXP, message = "Некорректный номер телефона")
    @Schema(description = "Номер телефона клиента", example = "+79378901234")
    private String phone;

    /**
     * Email клиента
     */
    @Email(message = "Некорректный email")
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
