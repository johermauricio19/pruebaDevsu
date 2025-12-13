package com.prueba.dev.cuentas.domain.service;

import com.prueba.dev.cuentas.domain.exception.CuentaNotFoundException;
import com.prueba.dev.cuentas.domain.exception.SaldoInsuficienteException;
import com.prueba.dev.cuentas.domain.model.Cuenta;
import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.port.CuentaRepositoryPort;
import com.prueba.dev.cuentas.domain.port.MovimientoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CuentaServiceTest {

    @Mock
    private CuentaRepositoryPort cuentaRepositoryPort;

    @Mock
    private MovimientoRepositoryPort movimientoRepositoryPort;

    @InjectMocks
    private CuentaService cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = new Cuenta(1L, "123456789", "Ahorros", BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), "ACTIVO");
        cuenta.setId(1L);
    }

    @Test
    void createCuenta_ShouldReturnSavedCuenta() {
        when(cuentaRepositoryPort.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta result = cuentaService.createCuenta(cuenta);

        assertNotNull(result);
        assertEquals(cuenta.getId(), result.getId());
        verify(cuentaRepositoryPort, times(1)).save(cuenta);
    }

    @Test
    void getCuentaById_ShouldReturnCuenta_WhenExists() {
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuenta));

        Cuenta result = cuentaService.getCuentaById(1L);

        assertNotNull(result);
        assertEquals(cuenta.getId(), result.getId());
        verify(cuentaRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getCuentaById_ShouldThrowException_WhenNotExists() {
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CuentaNotFoundException.class, () -> cuentaService.getCuentaById(1L));
        verify(cuentaRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getAllCuentas_ShouldReturnListOfCuentas() {
        List<Cuenta> cuentas = Arrays.asList(cuenta);
        when(cuentaRepositoryPort.findAll()).thenReturn(cuentas);

        List<Cuenta> result = cuentaService.getAllCuentas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cuentaRepositoryPort, times(1)).findAll();
    }

    @Test
    void updateCuenta_ShouldReturnUpdatedCuenta() {
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaRepositoryPort.save(any(Cuenta.class))).thenReturn(cuenta);

        Cuenta updatedCuenta = new Cuenta(1L, "987654321", "Corriente", BigDecimal.valueOf(2000), BigDecimal.valueOf(2000), "ACTIVO");
        Cuenta result = cuentaService.updateCuenta(1L, updatedCuenta);

        assertNotNull(result);
        verify(cuentaRepositoryPort, times(1)).findById(1L);
        verify(cuentaRepositoryPort, times(1)).save(any(Cuenta.class));
    }

    @Test
    void deleteCuenta_ShouldCallRepositoryDelete() {
        when(cuentaRepositoryPort.existsById(1L)).thenReturn(true);

        cuentaService.deleteCuenta(1L);

        verify(cuentaRepositoryPort, times(1)).existsById(1L);
        verify(cuentaRepositoryPort, times(1)).deleteById(1L);
    }

    @Test
    void depositar_ShouldIncreaseSaldoAndCreateMovimiento() {
        BigDecimal deposito = BigDecimal.valueOf(500);
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaRepositoryPort.save(any(Cuenta.class))).thenReturn(cuenta);

        Movimiento movimiento = new Movimiento(1L, "Deposito", deposito, BigDecimal.valueOf(1500));
        when(movimientoRepositoryPort.save(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento result = cuentaService.depositar(1L, deposito);

        assertNotNull(result);
        assertEquals(deposito, result.getValor());
        assertEquals(BigDecimal.valueOf(1500), result.getSaldo());
        verify(cuentaRepositoryPort, times(1)).findById(1L);
        verify(cuentaRepositoryPort, times(1)).save(any(Cuenta.class));
        verify(movimientoRepositoryPort, times(1)).save(any(Movimiento.class));
    }

    @Test
    void retirar_ShouldDecreaseSaldoAndCreateMovimiento_WhenSaldoSufficient() {
        BigDecimal retiro = BigDecimal.valueOf(300);
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaRepositoryPort.save(any(Cuenta.class))).thenReturn(cuenta);

        Movimiento movimiento = new Movimiento(1L, "Retiro", retiro.negate(), BigDecimal.valueOf(700));
        when(movimientoRepositoryPort.save(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento result = cuentaService.retirar(1L, retiro);

        assertNotNull(result);
        assertEquals(retiro.negate(), result.getValor());
        assertEquals(BigDecimal.valueOf(700), result.getSaldo());
        verify(cuentaRepositoryPort, times(1)).findById(1L);
        verify(cuentaRepositoryPort, times(1)).save(any(Cuenta.class));
        verify(movimientoRepositoryPort, times(1)).save(any(Movimiento.class));
    }

    @Test
    void retirar_ShouldThrowException_WhenSaldoInsufficient() {
        BigDecimal retiro = BigDecimal.valueOf(1500);
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuenta));

        assertThrows(SaldoInsuficienteException.class, () -> cuentaService.retirar(1L, retiro));
        verify(cuentaRepositoryPort, times(1)).findById(1L);
        verify(cuentaRepositoryPort, never()).save(any(Cuenta.class));
        verify(movimientoRepositoryPort, never()).save(any(Movimiento.class));
    }
}