package com.prueba.dev.cuentas.infrastructure.controller;

import com.prueba.dev.cuentas.application.dto.request.MovimientoRequest;
import com.prueba.dev.cuentas.application.dto.response.GenericResponse;
import com.prueba.dev.cuentas.application.dto.MovimientoDTO;
import com.prueba.dev.cuentas.application.service.MovimientoApplicationService;
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
 * Controlador REST para operaciones de movimientos.
 * Proporciona endpoints para CRUD de movimientos.
 */
@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimientos", description = "API para gestión de movimientos bancarios")
public class MovimientoController {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoController.class);

    private final MovimientoApplicationService movimientoApplicationService;
    private final ObjectMapper objectMapper;

    public MovimientoController(MovimientoApplicationService movimientoApplicationService, ObjectMapper objectMapper) {
        this.movimientoApplicationService = movimientoApplicationService;
        this.objectMapper = objectMapper;
    }

    /**
     * Crea un nuevo movimiento.
     * @param request La solicitud con los datos del movimiento.
     * @return La respuesta con el movimiento creado.
     */
    @PostMapping
    @Operation(summary = "Crear movimiento", description = "Crea un nuevo movimiento bancario")
    public ResponseEntity<GenericResponse> createMovimiento(@Valid @RequestBody MovimientoRequest request) {
        logger.info("Solicitud POST para crear movimiento");
        try {
            MovimientoDTO movimiento = movimientoApplicationService.createMovimiento(request);
            String jsonData = objectMapper.writeValueAsString(movimiento);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error al crear movimiento: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al crear movimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene un movimiento por su ID.
     * @param id El ID del movimiento.
     * @return La respuesta con el movimiento encontrado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento", description = "Obtiene un movimiento por su ID")
    public ResponseEntity<GenericResponse> getMovimientoById(@PathVariable Long id) {
        logger.info("Solicitud GET para movimiento con ID: {}", id);
        try {
            MovimientoDTO movimiento = movimientoApplicationService.getMovimientoById(id);
            String jsonData = objectMapper.writeValueAsString(movimiento);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener movimiento: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al obtener movimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene todos los movimientos.
     * @return Lista de movimientos.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los movimientos", description = "Obtiene una lista de todos los movimientos")
    public ResponseEntity<GenericResponse> getAllMovimientos() {
        logger.info("Solicitud GET para todos los movimientos");
        try {
            List<MovimientoDTO> movimientos = movimientoApplicationService.getAllMovimientos();
            String jsonData = objectMapper.writeValueAsString(movimientos);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al obtener movimientos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene movimientos por cuenta ID.
     * @param cuentaId El ID de la cuenta.
     * @return Lista de movimientos de la cuenta.
     */
    @GetMapping("/cuenta/{cuentaId}")
    @Operation(summary = "Obtener movimientos por cuenta", description = "Obtiene los movimientos de una cuenta específica")
    public ResponseEntity<GenericResponse> getMovimientosByCuentaId(@PathVariable Long cuentaId) {
        logger.info("Solicitud GET para movimientos de cuenta ID: {}", cuentaId);
        try {
            List<MovimientoDTO> movimientos = movimientoApplicationService.getMovimientosByCuentaId(cuentaId);
            String jsonData = objectMapper.writeValueAsString(movimientos);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos de cuenta: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al obtener movimientos de cuenta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Actualiza un movimiento existente.
     * @param id El ID del movimiento a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return La respuesta con el movimiento actualizado.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar movimiento", description = "Actualiza un movimiento existente")
    public ResponseEntity<GenericResponse> updateMovimiento(@PathVariable Long id, @Valid @RequestBody MovimientoRequest request) {
        logger.info("Solicitud PUT para actualizar movimiento con ID: {}", id);
        try {
            MovimientoDTO movimiento = movimientoApplicationService.updateMovimiento(id, request);
            String jsonData = objectMapper.writeValueAsString(movimiento);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al actualizar movimiento: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al actualizar movimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Actualiza parcialmente un movimiento.
     * @param id El ID del movimiento a actualizar.
     * @param request La solicitud con los datos a actualizar.
     * @return La respuesta con el movimiento actualizado.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar movimiento parcialmente", description = "Actualiza parcialmente un movimiento existente")
    public ResponseEntity<GenericResponse> patchMovimiento(@PathVariable Long id, @RequestBody MovimientoRequest request) {
        logger.info("Solicitud PATCH para movimiento con ID: {}", id);
        try {
            MovimientoDTO movimiento = movimientoApplicationService.updateMovimiento(id, request);
            String jsonData = objectMapper.writeValueAsString(movimiento);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al actualizar movimiento parcialmente: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al actualizar movimiento parcialmente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Elimina un movimiento por su ID.
     * @param id El ID del movimiento a eliminar.
     * @return Respuesta de éxito.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar movimiento", description = "Elimina un movimiento por su ID")
    public ResponseEntity<GenericResponse> deleteMovimiento(@PathVariable Long id) {
        logger.info("Solicitud DELETE para movimiento con ID: {}", id);
        try {
            movimientoApplicationService.deleteMovimiento(id);
            GenericResponse response = new GenericResponse(true, "Movimiento eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al eliminar movimiento: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al eliminar movimiento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}