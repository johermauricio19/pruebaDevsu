package com.prueba.dev.cuentas.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra una cuenta.
 */
public class CuentaNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * @param message Mensaje de la excepción.
     */
    public CuentaNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public CuentaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}