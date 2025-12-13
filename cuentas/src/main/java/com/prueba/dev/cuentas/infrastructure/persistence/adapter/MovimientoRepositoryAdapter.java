package com.prueba.dev.cuentas.infrastructure.persistence.adapter;

import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.port.MovimientoRepositoryPort;
import com.prueba.dev.cuentas.infrastructure.persistence.jpa.MovimientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador para el repositorio de movimientos.
 * Implementa el puerto de repositorio utilizando JPA.
 */
@Component
public class MovimientoRepositoryAdapter implements MovimientoRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoRepositoryAdapter.class);

    private final MovimientoRepository movimientoRepository;

    public MovimientoRepositoryAdapter(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    public Movimiento save(Movimiento movimiento) {
        logger.debug("Guardando movimiento con ID: {}", movimiento.getId());
        return movimientoRepository.save(movimiento);
    }

    @Override
    public Optional<Movimiento> findById(Long id) {
        logger.debug("Buscando movimiento con ID: {}", id);
        return movimientoRepository.findById(id);
    }

    @Override
    public List<Movimiento> findAll() {
        logger.debug("Obteniendo todos los movimientos");
        return movimientoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("Eliminando movimiento con ID: {}", id);
        movimientoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        logger.debug("Verificando existencia de movimiento con ID: {}", id);
        return movimientoRepository.existsById(id);
    }

    @Override
    public List<Movimiento> findByCuentaId(Long cuentaId) {
        logger.debug("Buscando movimientos para cuenta ID: {}", cuentaId);
        return movimientoRepository.findByCuentaId(cuentaId);
    }

    @Override
    public List<Movimiento> findByCuentaIdOrderByFechaDesc(Long cuentaId) {
        logger.debug("Buscando movimientos ordenados para cuenta ID: {}", cuentaId);
        return movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId);
    }

    @Override
    public List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, java.time.LocalDateTime inicio, java.time.LocalDateTime fin) {
        logger.debug("Buscando movimientos para cuenta ID: {} entre {} y {}", cuentaId, inicio, fin);
        return movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, inicio, fin);
    }
}