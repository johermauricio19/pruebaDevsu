package com.prueba.dev.cuentas.application.dto.response;

import com.prueba.dev.cuentas.domain.model.Cuenta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de una cuenta.
 */
public class CuentaResponse {

    private Long id;
    private Long clienteId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldo;
    private String estado;
    private LocalDateTime fechaCreacion;

    // Constructor vacío
    public CuentaResponse() {}

    // Constructor desde entidad
    public CuentaResponse(Cuenta cuenta) {
        this.id = cuenta.getId();
        this.clienteId = cuenta.getClienteId();
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.tipoCuenta = cuenta.getTipoCuenta();
        this.saldoInicial = cuenta.getSaldoInicial();
        this.saldo = cuenta.getSaldo();
        this.estado = cuenta.getEstado();
        this.fechaCreacion = cuenta.getFechaCreacion();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}