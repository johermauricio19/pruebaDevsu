package com.prueba.dev.cuentas.application.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para la solicitud de creación/actualización de una cuenta.
 */
public class CuentaRequest {

    @NotNull(message = "El cliente ID es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(max = 50, message = "El número de cuenta no puede exceder 50 caracteres")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "Ahorros|Corriente|Inversion", message = "El tipo de cuenta debe ser Ahorros, Corriente o Inversion")
    private String tipoCuenta;

    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", message = "El saldo inicial debe ser mayor o igual a 0")
    private BigDecimal saldoInicial;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVA|CERRADA|SUSPENDIDA", message = "El estado debe ser ACTIVA, CERRADA o SUSPENDIDA")
    private String estado;

    // Constructor vacío
    public CuentaRequest() {}

    // Constructor con parámetros
    public CuentaRequest(Long clienteId, String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial, String estado) {
        this.clienteId = clienteId;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}