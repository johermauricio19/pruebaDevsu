package com.prueba.dev.clientes.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para la solicitud de creación/actualización de un cliente.
 */
public class ClienteRequest {

    @NotNull(message = "El ID de la persona es obligatorio")
    private Long personaId;

    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 8, message = "La clave debe tener al menos 8 caracteres")
    private String clave;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|BLOQUEADO", message = "El estado debe ser ACTIVO, INACTIVO o BLOQUEADO")
    private String estado;

    // Constructor vacío
    public ClienteRequest() {}

    // Constructor con parámetros
    public ClienteRequest(Long personaId, String clave, String estado) {
        this.personaId = personaId;
        this.clave = clave;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}