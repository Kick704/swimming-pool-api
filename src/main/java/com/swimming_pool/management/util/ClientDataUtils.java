package com.swimming_pool.management.util;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;

import java.util.regex.Pattern;

/**
 * Утилитарный класс для работы с данными клиента между сущностью и DTO.
 * <p>Предоставляет статические поля и методы для работы напрямую через класс
 */
public final class ClientDataUtils {

    private ClientDataUtils() {
    }

    /**
     * Шаблон номера телефона.
     * <p>Допустимые форматы:
     * <ul>
     *     <li>+7XXXXXXXXXX - международный формат для России, используется для представления в DTO</li>
     *     <li>8XXXXXXXXXX - формат номера внутри России</li>
     * </ul>
     */
    public static final String DTO_PHONE_REGEXP = "^(\\+7|8)\\d{10}$";

    /**
     * Форматирование номера телефона для DTO на основе формата номера в сущности
     *
     * @param entityPhone номер телефона сущности
     * @return номер телефона для DTO
     */
    public static String formatPhoneForDTO(String entityPhone) {
        String DTOPhone = "+7".concat(entityPhone);
        validateDTOPhoneFormat(DTOPhone);
        return DTOPhone;
    }

    /**
     * Форматирование номера телефона для сущности на основе формата номера в DTO
     *
     * @param DTOPhone номер телефона у DTO
     * @return номер телефона для сущности
     */
    public static String formatPhoneForEntity(String DTOPhone) {
        validateDTOPhoneFormat(DTOPhone);
        return DTOPhone.substring(DTOPhone.length() - 10);
    }

    /**
     * Валидация формата номера телефона в DTO
     *
     * @param DTOPhone номер телефона в DTO
     */
    private static void validateDTOPhoneFormat(String DTOPhone) {
        if (Pattern.matches(DTO_PHONE_REGEXP, DTOPhone)) {
            throw new SwimmingPoolManagementException(
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    String.format("Ошибка в формате номера телефона: %s", DTOPhone)
            );
        }
    }

}
