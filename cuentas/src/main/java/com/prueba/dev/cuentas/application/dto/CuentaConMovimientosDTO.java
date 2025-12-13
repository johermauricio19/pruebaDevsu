package com.prueba.dev.cuentas.application.dto;

import java.util.List;

/**
 * DTO para cuenta con sus movimientos.
 */
public class CuentaConMovimientosDTO {

    private CuentaDTO cuenta;
    private List<MovimientoDTO> movimientos;

    public CuentaConMovimientosDTO() {}

    public CuentaConMovimientosDTO(CuentaDTO cuenta, List<MovimientoDTO> movimientos) {
        this.cuenta = cuenta;
        this.movimientos = movimientos;
    }

    public CuentaDTO getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaDTO cuenta) {
        this.cuenta = cuenta;
    }

    public List<MovimientoDTO> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoDTO> movimientos) {
        this.movimientos = movimientos;
    }
}