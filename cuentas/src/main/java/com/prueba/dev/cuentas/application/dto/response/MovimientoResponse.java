package com.prueba.dev.cuentas.application.dto.response;

import com.prueba.dev.cuentas.domain.model.Movimiento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la respuesta de un movimiento.
 */
public class MovimientoResponse {

    private Long id;
    private Long cuentaId;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;

    // Constructor vacío
    public MovimientoResponse() {}

    // Constructor desde entidad
    public MovimientoResponse(Movimiento movimiento) {
        this.id = movimiento.getId();
        this.cuentaId = movimiento.getCuentaId();
        this.fecha = movimiento.getFecha();
        this.tipoMovimiento = movimiento.getTipoMovimiento();
        this.valor = movimiento.getValor();
        this.saldo = movimiento.getSaldo();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}