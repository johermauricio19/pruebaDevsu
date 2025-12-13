package com.prueba.dev.clientes.infrastructure.persistence.adapter;

import com.prueba.dev.clientes.domain.model.Persona;
import com.prueba.dev.clientes.domain.port.PersonaRepositoryPort;
import com.prueba.dev.clientes.infrastructure.persistence.jpa.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador para el repositorio de personas.
 * Implementa el puerto de repositorio utilizando JPA.
 */
@Component
public class PersonaRepositoryAdapter implements PersonaRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(PersonaRepositoryAdapter.class);

    private final PersonaRepository personaRepository;

    public PersonaRepositoryAdapter(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona save(Persona persona) {
        logger.debug("Guardando persona con ID: {}", persona.getId());
        return personaRepository.save(persona);
    }

    @Override
    public Optional<Persona> findById(Long id) {
        logger.debug("Buscando persona con ID: {}", id);
        return personaRepository.findById(id);
    }

    @Override
    public List<Persona> findAll() {
        logger.debug("Obteniendo todas las personas");
        return personaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("Eliminando persona con ID: {}", id);
        personaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        logger.debug("Verificando existencia de persona con ID: {}", id);
        return personaRepository.existsById(id);
    }

    @Override
    public Optional<Persona> findByIdentificacion(String identificacion) {
        logger.debug("Buscando persona con identificación: {}", identificacion);
        return personaRepository.findByIdentificacion(identificacion);
    }
}