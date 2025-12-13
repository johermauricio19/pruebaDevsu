# Usar imagen base de OpenJDK
FROM openjdk:11

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Construir la aplicación
RUN mvn clean package -DskipTests

# Exponer puerto
EXPOSE 8081

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/cuentas-1.0.0.jar"]