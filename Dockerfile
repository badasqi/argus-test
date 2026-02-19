# ----------------------
# 1. Build stage
# ----------------------
FROM maven:3.9.3-eclipse-temurin-11 AS build

WORKDIR /app

# Копируем pom.xml и скачиваем зависимости для кэша
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем весь проект
COPY . .

# Собираем jar, пропуская тесты
RUN mvn clean package -DskipTests

# ----------------------
# 2. Run stage
# ----------------------
FROM eclipse-temurin:11-jdk-jammy

WORKDIR /app

# Копируем jar из build stage
COPY --from=build /app/target/test-0.0.1-SNAPSHOT.jar app.jar

# Запуск приложения
ENTRYPOINT ["java","-jar","/app/app.jar"]
