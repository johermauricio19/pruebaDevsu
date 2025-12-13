package com.prueba.dev.cuentas.domain.service;

import com.prueba.dev.cuentas.domain.exception.MovimientoNotFoundException;
import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.port.MovimientoRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de dominio para operaciones de movimientos.
 * Contiene la lógica de negocio relacionada con movimientos.
 */
@Service
public class MovimientoService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoService.class);

    private final MovimientoRepositoryPort movimientoRepository;

    public MovimientoService(MovimientoRepositoryPort movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    /**
     * Crea un nuevo movimiento.
     * @param movimiento El movimiento a crear.
     * @return El movimiento creado.
     */
    public Movimiento createMovimiento(Movimiento movimiento) {
        logger.info("Creando movimiento para cuenta ID: {}", movimiento.getCuentaId());

        Movimiento savedMovimiento = movimientoRepository.save(movimiento);
        logger.info("Movimiento creado con ID: {}", savedMovimiento.getId());
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
     * @param id El ID del movimiento a actualizar.
     * @param movimiento Los nuevos datos del movimiento.
     * @return El movimiento actualizado.
     */
    public Movimiento updateMovimiento(Long id, Movimiento movimiento) {
        logger.info("Actualizando movimiento con ID: {}", id);

        Movimiento existingMovimiento = getMovimientoById(id);
        existingMovimiento.setTipoMovimiento(movimiento.getTipoMovimiento());
        existingMovimiento.setValor(movimiento.getValor());
        existingMovimiento.setSaldo(movimiento.getSaldo());

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

        if (!movimientoRepository.existsById(id)) {
            throw new MovimientoNotFoundException("Movimiento no encontrado con ID: " + id);
        }

        movimientoRepository.deleteById(id);
        logger.info("Movimiento eliminado con ID: {}", id);
    }
}