package com.prueba.dev.clientes.domain.service;

import com.prueba.dev.clientes.domain.exception.PersonaNotFoundException;
import com.prueba.dev.clientes.domain.model.Persona;
import com.prueba.dev.clientes.domain.port.PersonaRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de dominio para operaciones de personas.
 * Contiene la lógica de negocio relacionada con personas.
 */
@Service
public class PersonaService {

    private static final Logger logger = LoggerFactory.getLogger(PersonaService.class);

    private final PersonaRepositoryPort personaRepository;

    public PersonaService(PersonaRepositoryPort personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Crea una nueva persona.
     * @param persona La persona a crear.
     * @return La persona creada.
     */
    public Persona createPersona(Persona persona) {
        logger.info("Creando persona con identificación: {}", persona.getIdentificacion());

        // Verificar que la identificación no exista
        if (personaRepository.findByIdentificacion(persona.getIdentificacion()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una persona con la identificación: " + persona.getIdentificacion());
        }

        Persona savedPersona = personaRepository.save(persona);
        logger.info("Persona creada con ID: {}", savedPersona.getId());
        return savedPersona;
    }

    /**
     * Obtiene una persona por su ID.
     * @param id El ID de la persona.
     * @return La persona encontrada.
     */
    public Persona getPersonaById(Long id) {
        logger.info("Buscando persona con ID: {}", id);
        return personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con ID: " + id));
    }

    /**
     * Obtiene todas las personas.
     * @return Lista de personas.
     */
    public List<Persona> getAllPersonas() {
        logger.info("Obteniendo todas las personas");
        return personaRepository.findAll();
    }

    /**
     * Actualiza una persona existente.
     * @param id El ID de la persona a actualizar.
     * @param persona Los nuevos datos de la persona.
     * @return La persona actualizada.
     */
    public Persona updatePersona(Long id, Persona persona) {
        logger.info("Actualizando persona con ID: {}", id);

        Persona existingPersona = getPersonaById(id);
        existingPersona.setNombre(persona.getNombre());
        existingPersona.setEdad(persona.getEdad());
        existingPersona.setGenero(persona.getGenero());
        existingPersona.setIdentificacion(persona.getIdentificacion());
        existingPersona.setDireccion(persona.getDireccion());
        existingPersona.setTelefono(persona.getTelefono());

        Persona updatedPersona = personaRepository.save(existingPersona);
        logger.info("Persona actualizada con ID: {}", updatedPersona.getId());
        return updatedPersona;
    }

    /**
     * Elimina una persona por su ID.
     * @param id El ID de la persona a eliminar.
     */
    public void deletePersona(Long id) {
        logger.info("Eliminando persona con ID: {}", id);

        if (!personaRepository.existsById(id)) {
            throw new PersonaNotFoundException("Persona no encontrada con ID: " + id);
        }

        personaRepository.deleteById(id);
        logger.info("Persona eliminada con ID: {}", id);
    }
}