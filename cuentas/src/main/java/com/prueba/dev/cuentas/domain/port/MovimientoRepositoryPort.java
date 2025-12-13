package com.prueba.dev.cuentas.domain.port;

import com.prueba.dev.cuentas.domain.model.Movimiento;
import java.util.List;
import java.util.Optional;

/**
 * Puerto para operaciones de repositorio de movimientos.
 * Define las operaciones de acceso a datos para movimientos.
 */
public interface MovimientoRepositoryPort {

    /**
     * Guarda un movimiento en el repositorio.
     * @param movimiento El movimiento a guardar.
     * @return El movimiento guardado.
     */
    Movimiento save(Movimiento movimiento);

    /**
     * Busca un movimiento por su ID.
     * @param id El ID del movimiento.
     * @return Un Optional con el movimiento si se encuentra.
     */
    Optional<Movimiento> findById(Long id);

    /**
     * Obtiene todos los movimientos.
     * @return Lista de todos los movimientos.
     */
    List<Movimiento> findAll();

    /**
     * Elimina un movimiento por su ID.
     * @param id El ID del movimiento a eliminar.
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un movimiento con el ID dado.
     * @param id El ID del movimiento.
     * @return true si existe, false en caso contrario.
     */
    boolean existsById(Long id);

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