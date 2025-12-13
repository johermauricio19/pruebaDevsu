package com.prueba.dev.cuentas.infrastructure.persistence.adapter;

import com.prueba.dev.cuentas.domain.model.Cuenta;
import com.prueba.dev.cuentas.domain.port.CuentaRepositoryPort;
import com.prueba.dev.cuentas.infrastructure.persistence.jpa.CuentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador para el repositorio de cuentas.
 * Implementa el puerto de repositorio utilizando JPA.
 */
@Component
public class CuentaRepositoryAdapter implements CuentaRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(CuentaRepositoryAdapter.class);

    private final CuentaRepository cuentaRepository;

    public CuentaRepositoryAdapter(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        logger.debug("Guardando cuenta con ID: {}", cuenta.getId());
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        logger.debug("Buscando cuenta con ID: {}", id);
        return cuentaRepository.findById(id);
    }

    @Override
    public List<Cuenta> findAll() {
        logger.debug("Obteniendo todas las cuentas");
        return cuentaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("Eliminando cuenta con ID: {}", id);
        cuentaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        logger.debug("Verificando existencia de cuenta con ID: {}", id);
        return cuentaRepository.existsById(id);
    }

    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        logger.debug("Buscando cuenta con número: {}", numeroCuenta);
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Cuenta> findByClienteId(Long clienteId) {
        logger.debug("Buscando cuentas para cliente ID: {}", clienteId);
        return cuentaRepository.findByClienteId(clienteId);
    }
}