package com.prueba.dev.clientes.application.dto;

/**
 * DTO para representar un cliente en las operaciones CRUD.
 */
public class ClienteDTO {

    private Long id;
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Long personaId;
    private String clave;
    private String estado;
    private Integer numeroCuentas;

    public ClienteDTO() {}

    public ClienteDTO(Long id, String nombre, String genero, Integer edad, String identificacion, String direccion, String telefono, Long personaId, String clave, String estado, Integer numeroCuentas) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.personaId = personaId;
        this.clave = clave;
        this.estado = estado;
        this.numeroCuentas = numeroCuentas;
    }

    public ClienteDTO(Long personaId, String clave, String estado) {
        this.personaId = personaId;
        this.clave = clave;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
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

    public Integer getNumeroCuentas() {
        return numeroCuentas;
    }

    public void setNumeroCuentas(Integer numeroCuentas) {
        this.numeroCuentas = numeroCuentas;
    }
}