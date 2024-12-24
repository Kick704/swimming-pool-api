package com.swimming_pool.management;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения для управления плавательным бассейном.
 */
@SpringBootApplication
public class SwimmingPoolManagementRestApiApplication {

	/**
	 * Точка входа в приложение.
	 * <p>Метод загружает переменные окружения для конфигурации базы данных и устанавливает их в системные свойства,
	 * после чего запускает Spring Boot приложение.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_DRIVER_CLASS_NAME", dotenv.get("DB_DRIVER_CLASS_NAME"));

		SpringApplication.run(SwimmingPoolManagementRestApiApplication.class, args);
	}

}
