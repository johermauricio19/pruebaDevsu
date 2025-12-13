package com.prueba.dev.cuentas.infrastructure.persistence.jpa;

import com.prueba.dev.cuentas.domain.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Movimiento.
 * Proporciona operaciones CRUD básicas y personalizadas.
 */
@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    /**
     * Busca movimientos por cuenta ID.
     * @param cuentaId El ID de la cuenta.
     * @return Lista de movimientos de la cuenta.
     */
    List<Movimiento> findByCuentaId(Long cuentaId);

    /**
     * Busca movimientos por cuenta ID ordenados por fecha descendente.
     * @param cuentaId El ID de la cuenta.
     * @return Lista de movimientos ordenados.
     */
    List<Movimiento> findByCuentaIdOrderByFechaDesc(Long cuentaId);

    /**
     * Busca movimientos por cuenta ID y fecha entre un rango.
     * @param cuentaId El ID de la cuenta.
     * @param inicio Fecha de inicio.
     * @param fin Fecha de fin.
     * @return Lista de movimientos en el rango.
     */
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, java.time.LocalDateTime inicio, java.time.LocalDateTime fin);
}