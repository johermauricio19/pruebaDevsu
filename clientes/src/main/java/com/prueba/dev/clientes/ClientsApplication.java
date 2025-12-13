package com.prueba.dev.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del microservicio de clientes.
 * Inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class ClientsApplication {

    /**
     * Método principal para ejecutar la aplicación.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientsApplication.class, args);
    }
}