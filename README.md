# REST API для организации работы бассейна (регистрация клиентов, работа с записями на посещение)

## Описание проекта
REST API предоставляет функционал для управления клиентами и записями на посещение бассейна. 
Приложение разработано на основе **Spring Boot**, обеспечивает удобный способ взаимодействия с данными.

## Технологии и инструменты
- Язык: Java 17
- Фреймворк: Spring Boot
- База данных: PostgreSQL
- Инструмент миграции схемы БД: Liquibase
- Управление проектом: Maven
- Библиотека для маппинга данных между слоями (DTO и сущности): MapStruct
- Документация API: SpringDoc OpenAPI

## Настройка окружения
Перед запуском приложения выполняется загрузка переменных окружения для подключения к БД `swimming_pool` 
из файла `.env` в систему.
Пример содержимого файла:
```
DB_URL=jdbc:postgresql://localhost:5432/swimming_pool?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Yekaterinburg
DB_DRIVER_CLASS_NAME=org.postgresql.Driver
DB_USERNAME=username
DB_PASSWORD=pass
```

## Документация API
Полная документация API доступна через интерфейс Swagger:
URL Swagger: /swagger-ui/index.html
В Swagger представлены все эндпоинты, включая примеры запросов и ответов.

## Основные эндпоинты
### Работа с клиентами
- GET /api/v0/pool/client/get - Получение данных о клиенте.
- GET /api/v0/pool/client/all - Получение списка клиентов бассейна.
- POST /api/v0/pool/client/add - Добавление нового клиента.
- PUT /api/v0/pool/client/update - Обновление данных о клиенте.

### Работа с записями
- GET /api/v0/pool/client/all - Получение занятых записей на определённую дату.
- GET /api/v0/pool/client/available - Получение доступных записей на определённую дату.
- GET /api/v0/pool/client/by-client-name - Получение записей клиента по его ФИО.
- GET /api/v0/pool/client/by-date - Получение записей по дате посещения.
- POST /api/v0/pool/client/reserve - Добавить запись клиента на определённые дату и время.
- POST /api/v0/pool/client/reserve/multi-hour - Добавить записи клиента на определённые дату и время на несколько часов подряд.
- PUT /api/v0/pool/client/cancel - Отмена записи клиента на определённые дату и время