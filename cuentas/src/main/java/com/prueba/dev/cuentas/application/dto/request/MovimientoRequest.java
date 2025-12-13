package com.prueba.dev.cuentas.application.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para la solicitud de creación/actualización de un movimiento.
 */
public class MovimientoRequest {

    @NotNull(message = "El cuenta ID es obligatorio")
    private Long cuentaId;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "DEPOSITO|RETIRO|TRANSFERENCIA", message = "El tipo de movimiento debe ser DEPOSITO, RETIRO o TRANSFERENCIA")
    private String tipoMovimiento;

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a 0")
    private BigDecimal valor;

    // Constructor vacío
    public MovimientoRequest() {}

    // Constructor con parámetros
    public MovimientoRequest(Long cuentaId, String tipoMovimiento, BigDecimal valor) {
        this.cuentaId = cuentaId;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
    }

    // Getters y Setters
    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
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
}