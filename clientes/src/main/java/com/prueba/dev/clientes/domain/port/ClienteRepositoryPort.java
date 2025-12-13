package com.prueba.dev.clientes.domain.port;

import com.prueba.dev.clientes.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

/**
 * Puerto para operaciones de repositorio de clientes.
 * Define las operaciones de acceso a datos para clientes.
 */
public interface ClienteRepositoryPort {

    /**
     * Guarda un cliente en el repositorio.
     * @param cliente El cliente a guardar.
     * @return El cliente guardado.
     */
    Cliente save(Cliente cliente);

    /**
     * Busca un cliente por su ID.
     * @param id El ID del cliente.
     * @return Un Optional con el cliente si se encuentra.
     */
    Optional<Cliente> findById(Long id);

    /**
     * Obtiene todos los clientes.
     * @return Lista de todos los clientes.
     */
    List<Cliente> findAll();

    /**
     * Elimina un cliente por su ID.
     * @param id El ID del cliente a eliminar.
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un cliente con el ID dado.
     * @param id El ID del cliente.
     * @return true si existe, false en caso contrario.
     */
    boolean existsById(Long id);
}