package com.prueba.dev.clientes.domain.port;

import com.prueba.dev.clientes.domain.model.Persona;
import java.util.List;
import java.util.Optional;

/**
 * Puerto para operaciones de repositorio de personas.
 * Define las operaciones de acceso a datos para personas.
 */
public interface PersonaRepositoryPort {

    /**
     * Guarda una persona en el repositorio.
     * @param persona La persona a guardar.
     * @return La persona guardada.
     */
    Persona save(Persona persona);

    /**
     * Busca una persona por su ID.
     * @param id El ID de la persona.
     * @return Un Optional con la persona si se encuentra.
     */
    Optional<Persona> findById(Long id);

    /**
     * Obtiene todas las personas.
     * @return Lista de todas las personas.
     */
    List<Persona> findAll();

    /**
     * Elimina una persona por su ID.
     * @param id El ID de la persona a eliminar.
     */
    void deleteById(Long id);

    /**
     * Verifica si existe una persona con el ID dado.
     * @param id El ID de la persona.
     * @return true si existe, false en caso contrario.
     */
    boolean existsById(Long id);

    /**
     * Busca una persona por su identificación.
     * @param identificacion La identificación de la persona.
     * @return Un Optional con la persona si se encuentra.
     */
    Optional<Persona> findByIdentificacion(String identificacion);
}