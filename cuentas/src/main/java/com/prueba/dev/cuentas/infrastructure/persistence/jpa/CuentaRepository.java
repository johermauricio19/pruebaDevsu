package com.prueba.dev.cuentas.infrastructure.persistence.jpa;

import com.prueba.dev.cuentas.domain.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cuenta.
 * Proporciona operaciones CRUD básicas y personalizadas.
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    /**
     * Busca una cuenta por su número.
     * @param numeroCuenta El número de la cuenta.
     * @return Un Optional con la cuenta si se encuentra.
     */
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    /**
     * Busca cuentas por cliente ID.
     * @param clienteId El ID del cliente.
     * @return Lista de cuentas del cliente.
     */
    List<Cuenta> findByClienteId(Long clienteId);
}