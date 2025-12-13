package com.prueba.dev.cuentas.application.service;

import com.prueba.dev.cuentas.application.dto.request.MovimientoRequest;
import com.prueba.dev.cuentas.application.dto.MovimientoDTO;
import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.service.MovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para operaciones de movimientos.
 * Maneja los casos de uso de la aplicación relacionados con movimientos.
 */
@Service
@Transactional
public class MovimientoApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoApplicationService.class);

    private final MovimientoService movimientoService;

    public MovimientoApplicationService(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    /**
     * Crea un nuevo movimiento.
     * @param request La solicitud con los datos del movimiento.
     * @return El movimiento creado.
     */
    public MovimientoDTO createMovimiento(MovimientoRequest request) {
        logger.info("Creando movimiento para cuenta ID: {}", request.getCuentaId());

        Movimiento movimiento = new Movimiento(
                request.getCuentaId(),
                request.getTipoMovimiento(),
                request.getValor(),
                BigDecimal.ZERO // saldo se calcula en el servicio de dominio
        );

        Movimiento savedMovimiento = movimientoService.createMovimiento(movimiento);
        logger.info("Movimiento creado exitosamente con ID: {}", savedMovimiento.getId());

        return new MovimientoDTO(
                savedMovimiento.getId(),
                savedMovimiento.getCuentaId(),
                savedMovimiento.getFecha(),
                savedMovimiento.getTipoMovimiento(),
                savedMovimiento.getValor(),
                savedMovimiento.getSaldo()
        );
    }

    /**
     * Obtiene un movimiento por su ID.
     * @param id El ID del movimiento.
     * @return El movimiento encontrado.
     */
    @Transactional(readOnly = true)
    public MovimientoDTO getMovimientoById(Long id) {
        logger.info("Obteniendo movimiento con ID: {}", id);
        Movimiento movimiento = movimientoService.getMovimientoById(id);
        return new MovimientoDTO(
                movimiento.getId(),
                movimiento.getCuentaId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo()
        );
    }

    /**
     * Obtiene todos los movimientos.
     * @return Lista de movimientos.
     */
    @Transactional(readOnly = true)
    public List<MovimientoDTO> getAllMovimientos() {
        logger.info("Obteniendo todos los movimientos");
        List<Movimiento> movimientos = movimientoService.getAllMovimientos();
        return movimientos.stream()
                .map(movimiento -> new MovimientoDTO(
                        movimiento.getId(),
                        movimiento.getCuentaId(),
                        movimiento.getFecha(),
                        movimiento.getTipoMovimiento(),
                        movimiento.getValor(),
                        movimiento.getSaldo()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene movimientos por cuenta ID.
     * @param cuentaId El ID de la cuenta.
     * @return Lista de movimientos de la cuenta.
     */
    @Transactional(readOnly = true)
    public List<MovimientoDTO> getMovimientosByCuentaId(Long cuentaId) {
        logger.info("Obteniendo movimientos para cuenta ID: {}", cuentaId);
        List<Movimiento> movimientos = movimientoService.getMovimientosByCuentaId(cuentaId);
        return movimientos.stream()
                .map(movimiento -> new MovimientoDTO(
                        movimiento.getId(),
                        movimiento.getCuentaId(),
                        movimiento.getFecha(),
                        movimiento.getTipoMovimiento(),
                        movimiento.getValor(),
                        movimiento.getSaldo()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un movimiento existente.
     * @param id El ID del movimiento a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return El movimiento actualizado.
     */
    public MovimientoDTO updateMovimiento(Long id, MovimientoRequest request) {
        logger.info("Actualizando movimiento con ID: {}", id);

        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(request.getTipoMovimiento());
        movimiento.setValor(request.getValor());

        Movimiento updatedMovimiento = movimientoService.updateMovimiento(id, movimiento);
        logger.info("Movimiento actualizado exitosamente con ID: {}", updatedMovimiento.getId());

        return new MovimientoDTO(
                updatedMovimiento.getId(),
                updatedMovimiento.getCuentaId(),
                updatedMovimiento.getFecha(),
                updatedMovimiento.getTipoMovimiento(),
                updatedMovimiento.getValor(),
                updatedMovimiento.getSaldo()
        );
    }

    /**
     * Elimina un movimiento por su ID.
     * @param id El ID del movimiento a eliminar.
     */
    public void deleteMovimiento(Long id) {
        logger.info("Eliminando movimiento con ID: {}", id);
        movimientoService.deleteMovimiento(id);
        logger.info("Movimiento eliminado exitosamente con ID: {}", id);
    }
}