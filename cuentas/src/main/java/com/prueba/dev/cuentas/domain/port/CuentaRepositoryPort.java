package com.prueba.dev.cuentas.domain.port;

import com.prueba.dev.cuentas.domain.model.Cuenta;
import java.util.List;
import java.util.Optional;

/**
 * Puerto para operaciones de repositorio de cuentas.
 * Define las operaciones de acceso a datos para cuentas.
 */
public interface CuentaRepositoryPort {

    /**
     * Guarda una cuenta en el repositorio.
     * @param cuenta La cuenta a guardar.
     * @return La cuenta guardada.
     */
    Cuenta save(Cuenta cuenta);

    /**
     * Busca una cuenta por su ID.
     * @param id El ID de la cuenta.
     * @return Un Optional con la cuenta si se encuentra.
     */
    Optional<Cuenta> findById(Long id);

    /**
     * Obtiene todas las cuentas.
     * @return Lista de todas las cuentas.
     */
    List<Cuenta> findAll();

    /**
     * Elimina una cuenta por su ID.
     * @param id El ID de la cuenta a eliminar.
     */
    void deleteById(Long id);

    /**
     * Verifica si existe una cuenta con el ID dado.
     * @param id El ID de la cuenta.
     * @return true si existe, false en caso contrario.
     */
    boolean existsById(Long id);

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