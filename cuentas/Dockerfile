# Usar imagen base de Maven para construir
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Construir la aplicación
RUN mvn clean package -DskipTests -pl cuentas -am

# Usar imagen base de OpenJDK para ejecutar
FROM eclipse-temurin:21-jdk

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR construido
COPY --from=build /app/cuentas/target/cuentas-1.0.0.jar app.jar

# Exponer puerto
EXPOSE 8081

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]