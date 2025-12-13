package com.prueba.dev.cuentas.application.dto;

import java.util.List;

/**
 * DTO para el reporte de estado de cuenta.
 */
public class EstadoCuentaDTO {

    private Long clienteId;
    private List<CuentaConMovimientosDTO> cuentas;

    public EstadoCuentaDTO() {}

    public EstadoCuentaDTO(Long clienteId, List<CuentaConMovimientosDTO> cuentas) {
        this.clienteId = clienteId;
        this.cuentas = cuentas;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<CuentaConMovimientosDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaConMovimientosDTO> cuentas) {
        this.cuentas = cuentas;
    }
}