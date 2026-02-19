# Система планировщика задач

**Стек:**
- Java 11
- Spring Boot 2.7.18
- PostgreSQL 15

---

# Запуск через Docker и Docker Compose

Для поднятия системы планировщика задач с PostgreSQL можно использовать Docker и Docker Compose. (Я использовал Rancher Descktop) 

## 1. Сборка Docker-образа приложения

```bash
docker build -t tasks-app .
```

## 2. Запуск через Docker Compose

```bash
docker-compose up -d --build
```

- `-d` — запуск в фоне.  
- `--build` — пересборка образов перед запуском.

## 3. Проверка работы

- Приложение будет доступно на [http://localhost:8080](http://localhost:8080).  
- PostgreSQL доступен на порту `5432`.  

## 4. Остановка и удаление контейнеров

```bash
docker-compose down
```

- Эта команда останавливает и удаляет контейнеры, но сохраняет данные в volume `pgdata`.


## 1. Функциональность

- Создание, просмотр и удаление задач через REST‑API
- Выполнение задач по расписанию:
  - Изменение статуса группы (ACTIVE ↔ INACTIVE)
  - Перевод сотрудника в другое подразделение
  - Запись сообщения в лог

---

## 2. API эндпоинты

- `POST /tasks` — создание задачи
- `GET /tasks` — просмотр всех задач
- `GET /tasks/{id}` — просмотр конкретной задачи
- `DELETE /tasks/{id}` — удаление задачи

---

# Тестовые данные и примеры задач

## 1. Группы

```sql
INSERT INTO argus_group (id, active) VALUES ('11111111-1111-1111-1111-111111111111', true);
INSERT INTO argus_group (id, active) VALUES ('22222222-2222-2222-2222-222222222222', false);
```

## 2. Сотрудники

```sql
INSERT INTO argus_worker (id, group_id) VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111');
INSERT INTO argus_worker (id, group_id) VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '11111111-1111-1111-1111-111111111111');
INSERT INTO argus_worker (id, group_id) VALUES ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', '22222222-2222-2222-2222-222222222222');
```

## 3. Примеры задач

### 3.1 Перевод сотрудника в другую группу

**JSON:**
```json
{
  "executionTime": "2026-02-19T15:00:00",
  "payload": {
    "type": "WORKER_TRANSFER",
    "workerId": "aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1",
    "groupId": "22222222-2222-2222-2222-222222222222"
  }
}
```

**curl:**
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "executionTime": "2026-02-19T15:00:00",
    "payload": {
      "type": "WORKER_TRANSFER",
      "workerId": "aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1",
      "groupId": "22222222-2222-2222-2222-222222222222"
    }
  }'
```

### 3.2 Отключение группы

**JSON:**
```json
{
  "executionTime": "2026-02-19T15:05:00",
  "payload": {
    "type": "GROUP_STATUS",
    "groupId": "11111111-1111-1111-1111-111111111111",
    "active": false
  }
}
```

**curl:**
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "executionTime": "2026-02-19T15:05:00",
    "payload": {
      "type": "GROUP_STATUS",
      "groupId": "11111111-1111-1111-1111-111111111111",
      "active": false
    }
  }'
```

### 3.3 Уведомление

**JSON:**
```json
{
  "executionTime": "2026-02-19T16:00:00",
  "payload": {
    "type": "LOG_NOTIFICATION",
    "notificationText": "Группа была отключена",
    "eventDate": "2026-02-20"
  }
}
```

**curl:**
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "executionTime": "2026-02-19T16:00:00",
    "payload": {
      "type": "LOG_NOTIFICATION",
      "notificationText": "Группа была отключена",
      "eventDate": "2026-02-20"
    }
  }'
```
