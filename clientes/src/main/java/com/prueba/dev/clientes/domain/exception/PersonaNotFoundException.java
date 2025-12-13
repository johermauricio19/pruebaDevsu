package com.prueba.dev.clientes.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra una persona.
 */
public class PersonaNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * @param message Mensaje de la excepción.
     */
    public PersonaNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public PersonaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}