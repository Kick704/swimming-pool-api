package com.swimming_pool.management.exception_handler;

/**
 * Кастомное исключение для управления ошибками в приложении с привязкой кода {@link ErrorCode}
 */
public class SwimmingPoolManagementException extends RuntimeException {

    /**
     * Код ошибки
     */
    private final ErrorCode errorCode;

    /**
     * Конструктор для создания исключения с кодом ошибки и сообщением
     *
     * @param errorCode код ошибки, описывающий тип ошибки {@link ErrorCode}
     * @param message текстовое описание ошибки
     */
    public SwimmingPoolManagementException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Геттер кода ошибки
     *
     * @return код ошибки
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
