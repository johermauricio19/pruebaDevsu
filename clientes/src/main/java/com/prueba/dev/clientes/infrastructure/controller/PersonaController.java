package com.prueba.dev.clientes.infrastructure.controller;

import com.prueba.dev.clientes.application.dto.request.PersonaRequest;
import com.prueba.dev.clientes.application.dto.response.GenericResponse;
import com.prueba.dev.clientes.application.dto.PersonaDTO;
import com.prueba.dev.clientes.application.service.PersonaApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones de personas.
 * Proporciona endpoints para CRUD de personas.
 */
@RestController
@RequestMapping("/personas")
@Tag(name = "Personas", description = "API para gestión de personas")
public class PersonaController {

    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);

    private final PersonaApplicationService personaApplicationService;
    private final ObjectMapper objectMapper;

    public PersonaController(PersonaApplicationService personaApplicationService, ObjectMapper objectMapper) {
        this.personaApplicationService = personaApplicationService;
        this.objectMapper = objectMapper;
    }

    /**
     * Crea una nueva persona.
     * @param request La solicitud con los datos de la persona.
     * @return La respuesta genérica con el resultado.
     */
    @PostMapping
    @Operation(summary = "Crear persona", description = "Crea una nueva persona en el sistema")
    public ResponseEntity<GenericResponse> createPersona(@Valid @RequestBody PersonaRequest request) {
        try {
            logger.info("Solicitud POST para crear persona");
            PersonaDTO persona = personaApplicationService.createPersona(request);
            String personaJson = objectMapper.writeValueAsString(persona);
            logger.info("Persona creada exitosamente con ID: {}", persona.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(true, personaJson));
        } catch (Exception e) {
            logger.error("Error al crear persona: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al crear persona: " + e.getMessage()));
        }
    }

    /**
     * Obtiene una persona por su ID.
     * @param id El ID de la persona.
     * @return La respuesta genérica con la persona encontrada.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener persona", description = "Obtiene una persona por su ID")
    public ResponseEntity<GenericResponse> getPersonaById(@PathVariable Long id) {
        try {
            logger.info("Solicitud GET para persona con ID: {}", id);
            PersonaDTO persona = personaApplicationService.getPersonaById(id);
            String personaJson = objectMapper.writeValueAsString(persona);
            return ResponseEntity.ok(new GenericResponse(true, personaJson));
        } catch (Exception e) {
            logger.error("Error al obtener persona: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(false, "Persona no encontrada: " + e.getMessage()));
        }
    }

    /**
     * Obtiene todas las personas.
     * @return La respuesta genérica con la lista de personas.
     */
    @GetMapping
    @Operation(summary = "Obtener todas las personas", description = "Obtiene una lista de todas las personas")
    public ResponseEntity<GenericResponse> getAllPersonas() {
        try {
            logger.info("Solicitud GET para todas las personas");
            List<PersonaDTO> personas = personaApplicationService.getAllPersonas();
            String personasJson = objectMapper.writeValueAsString(personas);
            return ResponseEntity.ok(new GenericResponse(true, personasJson));
        } catch (Exception e) {
            logger.error("Error al obtener personas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al obtener personas: " + e.getMessage()));
        }
    }

    /**
     * Actualiza una persona existente.
     * @param id El ID de la persona a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return La respuesta genérica con la persona actualizada.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona", description = "Actualiza una persona existente")
    public ResponseEntity<GenericResponse> updatePersona(@PathVariable Long id, @Valid @RequestBody PersonaRequest request) {
        try {
            logger.info("Solicitud PUT para actualizar persona con ID: {}", id);
            PersonaDTO persona = personaApplicationService.updatePersona(id, request);
            String personaJson = objectMapper.writeValueAsString(persona);
            logger.info("Persona actualizada exitosamente con ID: {}", persona.getId());
            return ResponseEntity.ok(new GenericResponse(true, personaJson));
        } catch (Exception e) {
            logger.error("Error al actualizar persona: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al actualizar persona: " + e.getMessage()));
        }
    }

    /**
     * Actualiza parcialmente una persona.
     * @param id El ID de la persona a actualizar.
     * @param request La solicitud con los datos a actualizar.
     * @return La respuesta genérica con la persona actualizada.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar persona parcialmente", description = "Actualiza parcialmente una persona existente")
    public ResponseEntity<GenericResponse> patchPersona(@PathVariable Long id, @RequestBody PersonaRequest request) {
        try {
            logger.info("Solicitud PATCH para persona con ID: {}", id);
            // Para simplificar, usamos el mismo método de update
            PersonaDTO persona = personaApplicationService.updatePersona(id, request);
            String personaJson = objectMapper.writeValueAsString(persona);
            logger.info("Persona actualizada parcialmente con ID: {}", persona.getId());
            return ResponseEntity.ok(new GenericResponse(true, personaJson));
        } catch (Exception e) {
            logger.error("Error al actualizar parcialmente persona: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al actualizar parcialmente persona: " + e.getMessage()));
        }
    }

    /**
     * Elimina una persona por su ID.
     * @param id El ID de la persona a eliminar.
     * @return La respuesta genérica con el resultado.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona", description = "Elimina una persona por su ID")
    public ResponseEntity<GenericResponse> deletePersona(@PathVariable Long id) {
        try {
            logger.info("Solicitud DELETE para persona con ID: {}", id);
            personaApplicationService.deletePersona(id);
            logger.info("Persona eliminada exitosamente con ID: {}", id);
            return ResponseEntity.ok(new GenericResponse(true, "Persona eliminada exitosamente"));
        } catch (Exception e) {
            logger.error("Error al eliminar persona: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al eliminar persona: " + e.getMessage()));
        }
    }
}