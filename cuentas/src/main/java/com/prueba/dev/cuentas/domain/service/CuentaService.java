package com.prueba.dev.cuentas.domain.service;

import com.prueba.dev.cuentas.application.dto.CuentaConMovimientosDTO;
import com.prueba.dev.cuentas.application.dto.CuentaDTO;
import com.prueba.dev.cuentas.application.dto.EstadoCuentaDTO;
import com.prueba.dev.cuentas.application.dto.MovimientoDTO;
import com.prueba.dev.cuentas.domain.exception.CuentaNotFoundException;
import com.prueba.dev.cuentas.domain.exception.SaldoInsuficienteException;
import com.prueba.dev.cuentas.domain.model.Cuenta;
import com.prueba.dev.cuentas.domain.model.Movimiento;
import com.prueba.dev.cuentas.domain.port.CuentaRepositoryPort;
import com.prueba.dev.cuentas.domain.port.MovimientoRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de dominio para operaciones de cuentas.
 * Contiene la lógica de negocio relacionada con cuentas y movimientos.
 */
@Service
public class CuentaService {

    private static final Logger logger = LoggerFactory.getLogger(CuentaService.class);

    private final CuentaRepositoryPort cuentaRepository;
    private final MovimientoRepositoryPort movimientoRepository;

    public CuentaService(CuentaRepositoryPort cuentaRepository, MovimientoRepositoryPort movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    /**
     * Crea una nueva cuenta.
     * @param cuenta La cuenta a crear.
     * @return La cuenta creada.
     */
    public Cuenta createCuenta(Cuenta cuenta) {
        logger.info("Creando cuenta para cliente ID: {}", cuenta.getClienteId());

        // Verificar que no exista una cuenta con el mismo número
        if (cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta con el número: " + cuenta.getNumeroCuenta());
        }

        Cuenta savedCuenta = cuentaRepository.save(cuenta);
        logger.info("Cuenta creada con ID: {}", savedCuenta.getId());
        return savedCuenta;
    }

    /**
     * Obtiene una cuenta por su ID.
     * @param id El ID de la cuenta.
     * @return La cuenta encontrada.
     */
    public Cuenta getCuentaById(Long id) {
        logger.info("Buscando cuenta con ID: {}", id);
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada con ID: " + id));
    }

    /**
     * Obtiene todas las cuentas.
     * @return Lista de cuentas.
     */
    public List<Cuenta> getAllCuentas() {
        logger.info("Obteniendo todas las cuentas");
        return cuentaRepository.findAll();
    }

    /**
     * Obtiene cuentas por cliente ID.
     * @param clienteId El ID del cliente.
     * @return Lista de cuentas del cliente.
     */
    public List<Cuenta> getCuentasByClienteId(Long clienteId) {
        logger.info("Obteniendo cuentas para cliente ID: {}", clienteId);
        return cuentaRepository.findByClienteId(clienteId);
    }

    /**
     * Actualiza una cuenta existente.
     * @param id El ID de la cuenta a actualizar.
     * @param cuenta Los nuevos datos de la cuenta.
     * @return La cuenta actualizada.
     */
    public Cuenta updateCuenta(Long id, Cuenta cuenta) {
        logger.info("Actualizando cuenta con ID: {}", id);

        Cuenta existingCuenta = getCuentaById(id);
        existingCuenta.setTipoCuenta(cuenta.getTipoCuenta());
        existingCuenta.setEstado(cuenta.getEstado());

        Cuenta updatedCuenta = cuentaRepository.save(existingCuenta);
        logger.info("Cuenta actualizada con ID: {}", updatedCuenta.getId());
        return updatedCuenta;
    }

    /**
     * Elimina una cuenta por su ID.
     * @param id El ID de la cuenta a eliminar.
     */
    public void deleteCuenta(Long id) {
        logger.info("Eliminando cuenta con ID: {}", id);

        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNotFoundException("Cuenta no encontrada con ID: " + id);
        }

        cuentaRepository.deleteById(id);
        logger.info("Cuenta eliminada con ID: {}", id);
    }

    /**
     * Realiza un depósito en una cuenta.
     * @param cuentaId El ID de la cuenta.
     * @param valor El valor a depositar.
     * @return El movimiento creado.
     */
    public Movimiento depositar(Long cuentaId, BigDecimal valor) {
        logger.info("Depositando {} en cuenta ID: {}", valor, cuentaId);

        Cuenta cuenta = getCuentaById(cuentaId);
        BigDecimal nuevoSaldo = cuenta.getSaldo().add(valor);
        cuenta.setSaldo(nuevoSaldo);

        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento(cuentaId, "DEPOSITO", valor, nuevoSaldo);
        Movimiento savedMovimiento = movimientoRepository.save(movimiento);

        logger.info("Depósito realizado, nuevo saldo: {}", nuevoSaldo);
        return savedMovimiento;
    }

    /**
     * Realiza un retiro de una cuenta.
     * @param cuentaId El ID de la cuenta.
     * @param valor El valor a retirar.
     * @return El movimiento creado.
     */
    public Movimiento retirar(Long cuentaId, BigDecimal valor) {
        logger.info("Retirando {} de cuenta ID: {}", valor, cuentaId);

        Cuenta cuenta = getCuentaById(cuentaId);

        if (cuenta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para el retiro. Saldo actual: " + cuenta.getSaldo());
        }

        BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(valor);
        cuenta.setSaldo(nuevoSaldo);

        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento(cuentaId, "RETIRO", valor, nuevoSaldo);
        Movimiento savedMovimiento = movimientoRepository.save(movimiento);

        logger.info("Retiro realizado, nuevo saldo: {}", nuevoSaldo);
        return savedMovimiento;
    }

    /**
     * Genera el estado de cuenta para un cliente en un rango de fechas.
     * @param clienteId El ID del cliente.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return El estado de cuenta.
     */
    public EstadoCuentaDTO getEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        logger.info("Generando estado de cuenta para cliente ID: {} entre {} y {}", clienteId, fechaInicio, fechaFin);

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        List<CuentaConMovimientosDTO> cuentasConMovimientos = cuentas.stream().map(cuenta -> {
            LocalDateTime inicio = fechaInicio.atStartOfDay();
            LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), inicio, fin);

            CuentaDTO cuentaDTO = new CuentaDTO(cuenta.getId(), cuenta.getClienteId(), cuenta.getNumeroCuenta(), cuenta.getTipoCuenta(), cuenta.getSaldoInicial(), cuenta.getEstado(), cuenta.getSaldo());
            List<MovimientoDTO> movimientosDTO = movimientos.stream()
                    .map(m -> new MovimientoDTO(m.getId(), m.getCuentaId(), m.getFecha(), m.getTipoMovimiento(), m.getValor(), m.getSaldo()))
                    .collect(Collectors.toList());

            return new CuentaConMovimientosDTO(cuentaDTO, movimientosDTO);
        }).collect(Collectors.toList());

        EstadoCuentaDTO estadoCuenta = new EstadoCuentaDTO(clienteId, cuentasConMovimientos);
        logger.info("Estado de cuenta generado para cliente ID: {}", clienteId);
        return estadoCuenta;
    }
}