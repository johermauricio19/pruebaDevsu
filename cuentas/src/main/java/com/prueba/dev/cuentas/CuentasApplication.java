package com.prueba.dev.cuentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del microservicio de cuentas.
 * Inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class CuentasApplication {

    /**
     * Método principal para ejecutar la aplicación.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(CuentasApplication.class, args);
    }
}
