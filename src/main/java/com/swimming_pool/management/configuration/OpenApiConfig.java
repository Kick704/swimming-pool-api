package com.swimming_pool.management.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * Конфигурация сваггера для отображения информации о приложении в UI API-документации
 */
@Configuration
public class OpenApiConfig {

    /**
     * Заголовок API
     */
    @Value("${api-title}")
    private String apiTitle;

    /**
     * Версия API
     */
    @Value("${api-version}")
    private String apiVersion;

    /**
     * Описание API
     */
    @Value("${api-description}")
    private String apiDescription;

    /**
     * Декодирование строки из ISO-8859-1 в UTF-8
     *
     * @param value строка, которую нужно декодировать
     * @return декодированная строка в формате UTF-8
     */
    private String decodeToUTF8(String value) {
        return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * Создание объект OpenAPI с настроенной информацией о приложении.
     *
     * @return конфигурация сваггера.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(decodeToUTF8(apiTitle))
                        .version(apiVersion)
                        .description(decodeToUTF8(apiDescription)));
    }

}
