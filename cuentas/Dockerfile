# Usar imagen base de OpenJDK
FROM openjdk:11

# Establecer directorio de trabajo
WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Construir la aplicación
RUN mvn clean package -DskipTests -pl cuentas -am

# Usar imagen base de OpenJDK para ejecutar
FROM eclipse-temurin:21-jdk

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR construido
COPY --from=build /app/cuentas/target/cuentas-1.0.0.jar app.jar

# Exponer puerto
EXPOSE 8082

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/cuentas-1.0.0.jar"]