package com.swimming_pool.management.model.dto.request;

import com.swimming_pool.management.model.entity.Client;
import com.swimming_pool.management.util.ClientDataUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO для создания сущности клиента {@link Client}
 */
@Schema(description = "Данные для добавления клиента")
public class ClientCreationDTO {

    /**
     * ФИО клиента
     */
    @NotBlank(message = "Не введены ФИО")
    @Size(min = 2, max = 100, message = "Длина ФИО должна быть от 2 до 100 символов")
    @Schema(description = "ФИО клиента", example = "Иванов Иван Иванович")
    private String name;

    /**
     * Номер телефона клиента
     */
    @NotBlank(message = "Не введён номер телефона")
    @Pattern(regexp = ClientDataUtils.DTO_PHONE_REGEXP, message = "Некорректный номер телефона")
    @Schema(description = "Номер телефона клиента", example = "+79378901234")
    private String phone;

    /**
     * Email клиента
     */
    @NotBlank(message = "Не введён email")
    @Email(message = "Некорректный email")
    @Schema(description = "Email клиента", example = "ivanov@mail.com")
    private String email;

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
