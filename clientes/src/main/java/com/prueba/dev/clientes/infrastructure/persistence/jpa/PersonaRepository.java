package com.prueba.dev.clientes.infrastructure.persistence.jpa;

import com.prueba.dev.clientes.domain.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Persona.
 * Proporciona operaciones CRUD básicas y personalizadas.
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    /**
     * Busca una persona por su identificación.
     * @param identificacion La identificación de la persona.
     * @return Un Optional con la persona si se encuentra.
     */
    Optional<Persona> findByIdentificacion(String identificacion);
}