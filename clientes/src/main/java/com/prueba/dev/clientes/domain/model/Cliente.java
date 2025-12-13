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

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "contraseña", nullable = false, length = 255)
    private String contraseña;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|BLOQUEADO", message = "El estado debe ser ACTIVO, INACTIVO o BLOQUEADO")
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    // Constructor vacío para JPA
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(Persona persona, String contraseña, String estado) {
        this.persona = persona;
        this.contraseña = contraseña;
        this.estado = estado;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}