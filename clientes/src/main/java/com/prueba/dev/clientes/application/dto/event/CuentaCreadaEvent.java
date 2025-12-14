package com.prueba.dev.clientes.application.dto.event;

/**
 * Evento recibido cuando se crea una cuenta.
 */
public class CuentaCreadaEvent {

    private Long clienteId;
    private Long cuentaId;
    private String numeroCuenta;

    public CuentaCreadaEvent() {}

    public CuentaCreadaEvent(Long clienteId, Long cuentaId, String numeroCuenta) {
        this.clienteId = clienteId;
        this.cuentaId = cuentaId;
        this.numeroCuenta = numeroCuenta;
    }

    // Getters and setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}