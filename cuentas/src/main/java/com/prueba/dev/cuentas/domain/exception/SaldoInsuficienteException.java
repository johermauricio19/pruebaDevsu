package com.prueba.dev.cuentas.domain.exception;

/**
 * Excepción lanzada cuando no hay saldo suficiente para una operación.
 */
public class SaldoInsuficienteException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * @param message Mensaje de la excepción.
     */
    public SaldoInsuficienteException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }
}