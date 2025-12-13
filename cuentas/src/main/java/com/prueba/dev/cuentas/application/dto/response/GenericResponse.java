package com.prueba.dev.cuentas.application.dto.response;

/**
 * Respuesta genérica para todas las operaciones de la API.
 * status: true si la operación fue exitosa, false en caso contrario.
 * message: Mensaje de error o datos de respuesta en formato JSON.
 */
public class GenericResponse {

    private boolean status;
    private String message;

    public GenericResponse() {}

    public GenericResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}