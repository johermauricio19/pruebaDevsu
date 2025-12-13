package com.prueba.dev.clientes.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra un cliente.
 */
public class ClienteNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * @param message Mensaje de la excepción.
     */
    public ClienteNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public ClienteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}