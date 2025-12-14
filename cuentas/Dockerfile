FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -pl cuentas -am

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/cuentas/target/cuentas-1.0.0.jar app.jar
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
