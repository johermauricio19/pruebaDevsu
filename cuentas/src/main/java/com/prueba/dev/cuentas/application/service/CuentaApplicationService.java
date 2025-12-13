package com.prueba.dev.cuentas.application.service;

import com.prueba.dev.cuentas.application.dto.request.CuentaRequest;
import com.prueba.dev.cuentas.application.dto.CuentaDTO;
import com.prueba.dev.cuentas.application.dto.EstadoCuentaDTO;
import com.prueba.dev.cuentas.application.dto.MovimientoDTO;
import com.prueba.dev.cuentas.domain.model.Cuenta;
import com.prueba.dev.cuentas.domain.service.CuentaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para operaciones de cuentas.
 * Maneja los casos de uso de la aplicación relacionados con cuentas.
 */
@Service
@Transactional
public class CuentaApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(CuentaApplicationService.class);

    private final CuentaService cuentaService;

    public CuentaApplicationService(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    /**
     * Crea una nueva cuenta.
     * @param request La solicitud con los datos de la cuenta.
     * @return La respuesta con la cuenta creada.
     */
    public CuentaDTO createCuenta(CuentaRequest request) {
        logger.info("Creando cuenta para cliente ID: {}", request.getClienteId());

        Cuenta cuenta = new Cuenta(
                request.getClienteId(),
                request.getNumeroCuenta(),
                request.getTipoCuenta(),
                request.getSaldoInicial(),
                request.getSaldoInicial(), // saldo inicial igual al saldo
                request.getEstado()
        );

        Cuenta savedCuenta = cuentaService.createCuenta(cuenta);
        logger.info("Cuenta creada exitosamente con ID: {}", savedCuenta.getId());

        return new CuentaDTO(
                savedCuenta.getId(),
                savedCuenta.getClienteId(),
                savedCuenta.getNumeroCuenta(),
                savedCuenta.getTipoCuenta(),
                savedCuenta.getSaldoInicial(),
                savedCuenta.getEstado(),
                savedCuenta.getSaldo()
        );
    }

    /**
     * Obtiene una cuenta por su ID.
     * @param id El ID de la cuenta.
     * @return La cuenta encontrada.
     */
    @Transactional(readOnly = true)
    public CuentaDTO getCuentaById(Long id) {
        logger.info("Obteniendo cuenta con ID: {}", id);
        Cuenta cuenta = cuentaService.getCuentaById(id);
        return new CuentaDTO(
                cuenta.getId(),
                cuenta.getClienteId(),
                cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.getEstado(),
                cuenta.getSaldo()
        );
    }

    /**
     * Obtiene todas las cuentas.
     * @return Lista de cuentas.
     */
    @Transactional(readOnly = true)
    public List<CuentaDTO> getAllCuentas() {
        logger.info("Obteniendo todas las cuentas");
        List<Cuenta> cuentas = cuentaService.getAllCuentas();
        return cuentas.stream()
                .map(cuenta -> new CuentaDTO(
                        cuenta.getId(),
                        cuenta.getClienteId(),
                        cuenta.getNumeroCuenta(),
                        cuenta.getTipoCuenta(),
                        cuenta.getSaldoInicial(),
                        cuenta.getEstado(),
                        cuenta.getSaldo()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene cuentas por cliente ID.
     * @param clienteId El ID del cliente.
     * @return Lista de cuentas del cliente.
     */
    @Transactional(readOnly = true)
    public List<CuentaDTO> getCuentasByClienteId(Long clienteId) {
        logger.info("Obteniendo cuentas para cliente ID: {}", clienteId);
        List<Cuenta> cuentas = cuentaService.getCuentasByClienteId(clienteId);
        return cuentas.stream()
                .map(cuenta -> new CuentaDTO(
                        cuenta.getId(),
                        cuenta.getClienteId(),
                        cuenta.getNumeroCuenta(),
                        cuenta.getTipoCuenta(),
                        cuenta.getSaldoInicial(),
                        cuenta.getEstado(),
                        cuenta.getSaldo()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una cuenta existente.
     * @param id El ID de la cuenta a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return La cuenta actualizada.
     */
    public CuentaDTO updateCuenta(Long id, CuentaRequest request) {
        logger.info("Actualizando cuenta con ID: {}", id);

        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setEstado(request.getEstado());

        Cuenta updatedCuenta = cuentaService.updateCuenta(id, cuenta);
        logger.info("Cuenta actualizada exitosamente con ID: {}", updatedCuenta.getId());

        return new CuentaDTO(
                updatedCuenta.getId(),
                updatedCuenta.getClienteId(),
                updatedCuenta.getNumeroCuenta(),
                updatedCuenta.getTipoCuenta(),
                updatedCuenta.getSaldoInicial(),
                updatedCuenta.getEstado(),
                updatedCuenta.getSaldo()
        );
    }

    /**
     * Elimina una cuenta por su ID.
     * @param id El ID de la cuenta a eliminar.
     */
    public void deleteCuenta(Long id) {
        logger.info("Eliminando cuenta con ID: {}", id);
        cuentaService.deleteCuenta(id);
        logger.info("Cuenta eliminada exitosamente con ID: {}", id);
    }

    /**
     * Realiza un depósito en una cuenta.
     * @param cuentaId El ID de la cuenta.
     * @param valor El valor a depositar.
     * @return El movimiento creado.
     */
    public MovimientoDTO depositar(Long cuentaId, BigDecimal valor) {
        logger.info("Depositando {} en cuenta ID: {}", valor, cuentaId);
        var movimiento = cuentaService.depositar(cuentaId, valor);
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
     * Realiza un retiro de una cuenta.
     * @param cuentaId El ID de la cuenta.
     * @param valor El valor a retirar.
     * @return El movimiento creado.
     */
    public MovimientoDTO retirar(Long cuentaId, BigDecimal valor) {
        logger.info("Retirando {} de cuenta ID: {}", valor, cuentaId);
        var movimiento = cuentaService.retirar(cuentaId, valor);
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
     * Genera el estado de cuenta para un cliente.
     * @param clienteId El ID del cliente.
     * @param fechaInicio Fecha de inicio.
     * @param fechaFin Fecha de fin.
     * @return El estado de cuenta.
     */
    public EstadoCuentaDTO getEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        logger.info("Generando estado de cuenta para cliente ID: {}", clienteId);
        return cuentaService.getEstadoCuenta(clienteId, fechaInicio, fechaFin);
    }
}