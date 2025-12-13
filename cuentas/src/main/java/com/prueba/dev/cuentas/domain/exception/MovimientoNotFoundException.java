package com.prueba.dev.cuentas.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra un movimiento.
 */
public class MovimientoNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * @param message Mensaje de la excepción.
     */
    public MovimientoNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public MovimientoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}