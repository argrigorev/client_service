# Client Service

RESTful приложение для управления клиентами и их контактами.

## Технологии

- Java 17
- Spring Boot 3.2.5
- Spring Web, Spring Data JPA (Hibernate)
- PostgreSQL
- Swagger (springdoc-openapi)
- Docker, Docker Compose
- Lombok

## Запуск через Docker

### 1. Создать `.env` файл в корне проекта

```
DB_NAME=postgres
DB_USER=postgres
DB_PASSWORD=your_password
```

### 2. Собрать jar

```
mvn clean package -DskipTests
```

### 3. Запустить через Docker Compose

```
docker-compose up --build
```

Приложение будет доступно на `http://localhost:8080`.


## Запуск локально (без Docker)

### 1. Убедиться, что PostgreSQL запущен на порту 5432

### 2. Указать параметры подключения в `application.properties`

```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Запустить приложение

```
mvn spring-boot:run
```

## Swagger UI

После запуска документация доступна по адресу:

```
http://localhost:8080/swagger-ui/index.html
```

## Переменные окружения

| Переменная    | Описание              |
|---------------|-----------------------|
| `DB_NAME`     | Имя базы данных       |
| `DB_USER`     | Пользователь БД       |
| `DB_PASSWORD` | Пароль пользователя   |

## API эндпоинты

### Клиенты

- GET /api/clients — получить всех клиентов

- GET /api/clients/{id} — получить клиента по ID

- POST /api/clients — создать клиента

- PUT /api/clients/{id} — обновить клиента

- DELETE /api/clients/{id} — удалить клиента


### Контакты

- GET /api/contacts — получить все контакты

- GET /api/contacts/{id} — получить контакт по ID

- GET /api/contacts/client/{clientId} — получить контакты клиента

- POST /api/contacts — создать контакт

- PUT /api/contacts/{id} — обновить контакт

- DELETE /api/contacts/{id} — удалить контакт

### Примеры запросов

**Создать клиента:**
```
POST /api/clients
{
  "name": "Artem",
  "lastname": "Grigorev"
}
```

**Создать контакт:**
```
POST /api/contacts
{
  "phone": "89001234567",
  "email": "artem@example.com",
  "clientId": 1
}
```
