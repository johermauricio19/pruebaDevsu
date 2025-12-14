FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -pl clientes -am

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/clientes/target/clientes-1.0.0.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
