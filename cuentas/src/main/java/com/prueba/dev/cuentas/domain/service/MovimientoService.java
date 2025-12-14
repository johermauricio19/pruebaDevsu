package com.prueba.dev.cuentas.domain.service;

import com.prueba.dev.cuentas.domain.exception.MovimientoNotFoundException;
import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.port.MovimientoRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio de dominio para operaciones de movimientos.
 * Contiene la lógica de negocio relacionada con movimientos.
 */
@Service
public class MovimientoService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoService.class);

    private final MovimientoRepositoryPort movimientoRepository;
    private final CuentaService cuentaService;

    public MovimientoService(MovimientoRepositoryPort movimientoRepository, CuentaService cuentaService) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaService = cuentaService;
    }

    /**
     * Crea un nuevo movimiento.
     * @param movimiento El movimiento a crear.
     * @return El movimiento creado.
     */
    public Movimiento createMovimiento(Movimiento movimiento) {
        logger.info("Creando movimiento para cuenta ID: {}", movimiento.getCuentaId());

        // Calcular el saldo después del movimiento
        BigDecimal saldoActual = cuentaService.calcularSaldoActual(movimiento.getCuentaId());
        if ("DEPOSITO".equals(movimiento.getTipoMovimiento())) {
            saldoActual = saldoActual.add(movimiento.getValor());
        } else if ("RETIRO".equals(movimiento.getTipoMovimiento()) || "TRANSFERENCIA".equals(movimiento.getTipoMovimiento())) {
            saldoActual = saldoActual.subtract(movimiento.getValor());
        }
        movimiento.setSaldo(saldoActual);

        Movimiento savedMovimiento = movimientoRepository.save(movimiento);
        logger.info("Movimiento creado con ID: {}", savedMovimiento.getId());

        // Actualizar el saldo de la cuenta
        cuentaService.actualizarSaldoCuenta(movimiento.getCuentaId(), saldoActual);

        return savedMovimiento;
    }

    /**
     * Obtiene un movimiento por su ID.
     * @param id El ID del movimiento.
     * @return El movimiento encontrado.
     */
    public Movimiento getMovimientoById(Long id) {
        logger.info("Buscando movimiento con ID: {}", id);
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException("Movimiento no encontrado con ID: " + id));
    }

    /**
     * Obtiene todos los movimientos.
     * @return Lista de movimientos.
     */
    public List<Movimiento> getAllMovimientos() {
        logger.info("Obteniendo todos los movimientos");
        return movimientoRepository.findAll();
    }

    /**
     * Obtiene movimientos por cuenta ID.
     * @param cuentaId El ID de la cuenta.
     * @return Lista de movimientos de la cuenta.
     */
    public List<Movimiento> getMovimientosByCuentaId(Long cuentaId) {
        logger.info("Obteniendo movimientos para cuenta ID: {}", cuentaId);
        return movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
    }

    /**
     * Actualiza un movimiento existente.
     * Solo permite actualizar el valor.
     * @param id El ID del movimiento a actualizar.
     * @param movimiento Los nuevos datos del movimiento (solo valor).
     * @return El movimiento actualizado.
     */
    public Movimiento updateMovimiento(Long id, Movimiento movimiento) {
        logger.info("Actualizando movimiento con ID: {}", id);

        Movimiento existingMovimiento = getMovimientoById(id);
        existingMovimiento.setValor(movimiento.getValor());

        // Recalcular el saldo de la cuenta
        BigDecimal nuevoSaldo = cuentaService.calcularSaldoActual(existingMovimiento.getCuentaId());
        cuentaService.actualizarSaldoCuenta(existingMovimiento.getCuentaId(), nuevoSaldo);

        // Actualizar el saldo en el movimiento (aunque no se use directamente)
        existingMovimiento.setSaldo(nuevoSaldo);

        Movimiento updatedMovimiento = movimientoRepository.save(existingMovimiento);
        logger.info("Movimiento actualizado con ID: {}", updatedMovimiento.getId());
        return updatedMovimiento;
    }

    /**
     * Elimina un movimiento por su ID.
     * @param id El ID del movimiento a eliminar.
     */
    public void deleteMovimiento(Long id) {
        logger.info("Eliminando movimiento con ID: {}", id);

        Movimiento movimiento = getMovimientoById(id);
        Long cuentaId = movimiento.getCuentaId();

        movimientoRepository.deleteById(id);

        // Recalcular el saldo de la cuenta después de eliminar el movimiento
        BigDecimal nuevoSaldo = cuentaService.calcularSaldoActual(cuentaId);
        cuentaService.actualizarSaldoCuenta(cuentaId, nuevoSaldo);

        logger.info("Movimiento eliminado con ID: {}", id);
    }
}