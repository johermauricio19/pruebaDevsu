package com.prueba.dev.clientes.application.dto.request;

import jakarta.validation.constraints.*;

/**
 * DTO para la solicitud de creación/actualización de una persona.
 */
public class PersonaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Min(value = 18, message = "La edad debe ser al menos 18 años")
    private Integer edad;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "Masculino|Femenino|Otro", message = "El género debe ser Masculino, Femenino u Otro")
    private String genero;

    @NotBlank(message = "La identificación es obligatoria")
    @Size(max = 50, message = "La identificación no puede exceder 50 caracteres")
    private String identificacion;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccion;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;

    // Constructor vacío
    public PersonaRequest() {}

    // Constructor con parámetros
    public PersonaRequest(String nombre, Integer edad, String genero, String identificacion, String direccion, String telefono) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}