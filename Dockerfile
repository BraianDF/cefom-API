FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*-SNAPSHOT.jar app.jar

RUN mkdir /app/banco
COPY banco/bdsistema.mv.db /app/banco

RUN mkdir /app/uploads
COPY uploads /app/uploads

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]