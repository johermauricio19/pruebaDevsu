package com.prueba.dev.cuentas.domain.service;

import com.prueba.dev.cuentas.domain.exception.MovimientoNotFoundException;
import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.port.MovimientoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovimientoServiceTest {

    @Mock
    private MovimientoRepositoryPort movimientoRepositoryPort;

    @InjectMocks
    private MovimientoService movimientoService;

    private Movimiento movimiento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movimiento = new Movimiento(1L, "DEPOSITO", BigDecimal.valueOf(500), BigDecimal.valueOf(1500));
        movimiento.setId(1L);
    }

    @Test
    void createMovimiento_ShouldReturnSavedMovimiento() {
        when(movimientoRepositoryPort.save(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento result = movimientoService.createMovimiento(movimiento);

        assertNotNull(result);
        assertEquals(movimiento.getId(), result.getId());
        verify(movimientoRepositoryPort, times(1)).save(movimiento);
    }

    @Test
    void getMovimientoById_ShouldReturnMovimiento_WhenExists() {
        when(movimientoRepositoryPort.findById(1L)).thenReturn(Optional.of(movimiento));

        Movimiento result = movimientoService.getMovimientoById(1L);

        assertNotNull(result);
        assertEquals(movimiento.getId(), result.getId());
        verify(movimientoRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getMovimientoById_ShouldThrowException_WhenNotExists() {
        when(movimientoRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovimientoNotFoundException.class, () -> movimientoService.getMovimientoById(1L));
        verify(movimientoRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getAllMovimientos_ShouldReturnListOfMovimientos() {
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        when(movimientoRepositoryPort.findAll()).thenReturn(movimientos);

        List<Movimiento> result = movimientoService.getAllMovimientos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movimientoRepositoryPort, times(1)).findAll();
    }

    @Test
    void getMovimientosByCuentaId_ShouldReturnListOfMovimientos() {
        List<Movimiento> movimientos = Arrays.asList(movimiento);
        when(movimientoRepositoryPort.findByCuentaIdOrderByFechaDesc(1L)).thenReturn(movimientos);

        List<Movimiento> result = movimientoService.getMovimientosByCuentaId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movimientoRepositoryPort, times(1)).findByCuentaIdOrderByFechaDesc(1L);
    }

    @Test
    void updateMovimiento_ShouldReturnUpdatedMovimiento() {
        when(movimientoRepositoryPort.findById(1L)).thenReturn(Optional.of(movimiento));
        when(movimientoRepositoryPort.save(any(Movimiento.class))).thenReturn(movimiento);

        Movimiento updatedMovimiento = new Movimiento(1L, "Retiro", BigDecimal.valueOf(-200), BigDecimal.valueOf(1300));
        Movimiento result = movimientoService.updateMovimiento(1L, updatedMovimiento);

        assertNotNull(result);
        verify(movimientoRepositoryPort, times(1)).findById(1L);
        verify(movimientoRepositoryPort, times(1)).save(any(Movimiento.class));
    }

    @Test
    void deleteMovimiento_ShouldCallRepositoryDelete() {
        when(movimientoRepositoryPort.existsById(1L)).thenReturn(true);

        movimientoService.deleteMovimiento(1L);

        verify(movimientoRepositoryPort, times(1)).existsById(1L);
        verify(movimientoRepositoryPort, times(1)).deleteById(1L);
    }
}