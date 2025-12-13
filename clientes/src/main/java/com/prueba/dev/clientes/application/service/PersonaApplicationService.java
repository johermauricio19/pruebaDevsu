package com.prueba.dev.clientes.application.service;

import com.prueba.dev.clientes.application.dto.request.PersonaRequest;
import com.prueba.dev.clientes.application.dto.PersonaDTO;
import com.prueba.dev.clientes.domain.model.Persona;
import com.prueba.dev.clientes.domain.service.PersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para operaciones de personas.
 * Maneja los casos de uso de la aplicación relacionados con personas.
 */
@Service
@Transactional
public class PersonaApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(PersonaApplicationService.class);

    private final PersonaService personaService;

    public PersonaApplicationService(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * Crea una nueva persona.
     * @param request La solicitud con los datos de la persona.
     * @return La persona creada.
     */
    public PersonaDTO createPersona(PersonaRequest request) {
        logger.info("Creando persona con identificación: {}", request.getIdentificacion());

        Persona persona = new Persona(
                request.getNombre(),
                request.getEdad(),
                request.getGenero(),
                request.getIdentificacion(),
                request.getDireccion(),
                request.getTelefono()
        );

        Persona savedPersona = personaService.createPersona(persona);
        logger.info("Persona creada exitosamente con ID: {}", savedPersona.getId());

        return new PersonaDTO(
                savedPersona.getId(),
                savedPersona.getNombre(),
                savedPersona.getGenero(),
                savedPersona.getEdad(),
                savedPersona.getIdentificacion(),
                savedPersona.getDireccion(),
                savedPersona.getTelefono()
        );
    }

    /**
     * Obtiene una persona por su ID.
     * @param id El ID de la persona.
     * @return La persona encontrada.
     */
    @Transactional(readOnly = true)
    public PersonaDTO getPersonaById(Long id) {
        logger.info("Obteniendo persona con ID: {}", id);
        Persona persona = personaService.getPersonaById(id);
        return new PersonaDTO(
                persona.getId(),
                persona.getNombre(),
                persona.getGenero(),
                persona.getEdad(),
                persona.getIdentificacion(),
                persona.getDireccion(),
                persona.getTelefono()
        );
    }

    /**
     * Obtiene todas las personas.
     * @return Lista de personas.
     */
    @Transactional(readOnly = true)
    public List<PersonaDTO> getAllPersonas() {
        logger.info("Obteniendo todas las personas");
        List<Persona> personas = personaService.getAllPersonas();
        return personas.stream()
                .map(persona -> new PersonaDTO(
                        persona.getId(),
                        persona.getNombre(),
                        persona.getGenero(),
                        persona.getEdad(),
                        persona.getIdentificacion(),
                        persona.getDireccion(),
                        persona.getTelefono()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una persona existente.
     * @param id El ID de la persona a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return La persona actualizada.
     */
    public PersonaDTO updatePersona(Long id, PersonaRequest request) {
        logger.info("Actualizando persona con ID: {}", id);

        Persona persona = new Persona(
                request.getNombre(),
                request.getEdad(),
                request.getGenero(),
                request.getIdentificacion(),
                request.getDireccion(),
                request.getTelefono()
        );

        Persona updatedPersona = personaService.updatePersona(id, persona);
        logger.info("Persona actualizada exitosamente con ID: {}", updatedPersona.getId());

        return new PersonaDTO(
                updatedPersona.getId(),
                updatedPersona.getNombre(),
                updatedPersona.getGenero(),
                updatedPersona.getEdad(),
                updatedPersona.getIdentificacion(),
                updatedPersona.getDireccion(),
                updatedPersona.getTelefono()
        );
    }

    /**
     * Elimina una persona por su ID.
     * @param id El ID de la persona a eliminar.
     */
    public void deletePersona(Long id) {
        logger.info("Eliminando persona con ID: {}", id);
        personaService.deletePersona(id);
        logger.info("Persona eliminada exitosamente con ID: {}", id);
    }
}