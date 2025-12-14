package com.prueba.dev.clientes.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidad que representa a un cliente en el sistema.
 * Un cliente está asociado a una persona y tiene credenciales de acceso.
 */
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 8, message = "La clave debe tener al menos 8 caracteres")
    @Column(name = "clave", nullable = false, length = 255)
    private String clave;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|BLOQUEADO", message = "El estado debe ser ACTIVO, INACTIVO o BLOQUEADO")
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "numero_cuentas", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer numeroCuentas = 0;

    // Constructor vacío para JPA
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(Persona persona, String clave, String estado) {
        this.persona = persona;
        this.clave = clave;
        this.estado = estado;
        this.numeroCuentas = 0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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

    public Integer getNumeroCuentas() {
        return numeroCuentas;
    }

    public void setNumeroCuentas(Integer numeroCuentas) {
        this.numeroCuentas = numeroCuentas;
    }
}