package com.prueba.dev.clientes.infrastructure.controller;

import com.prueba.dev.clientes.application.dto.request.ClienteRequest;
import com.prueba.dev.clientes.application.dto.GenericResponse;
import com.prueba.dev.clientes.application.dto.ClienteDTO;
import com.prueba.dev.clientes.application.service.ClienteApplicationService;
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
 * Controlador REST para operaciones de clientes.
 * Proporciona endpoints para CRUD de clientes.
 */
@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteApplicationService clienteApplicationService;
    private final ObjectMapper objectMapper;

    public ClienteController(ClienteApplicationService clienteApplicationService, ObjectMapper objectMapper) {
        this.clienteApplicationService = clienteApplicationService;
        this.objectMapper = objectMapper;
    }

    /**
     * Crea un nuevo cliente.
     * @param request La solicitud con los datos del cliente.
     * @return La respuesta genérica con el resultado.
     */
    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente en el sistema")
    public ResponseEntity<GenericResponse> createCliente(@Valid @RequestBody ClienteRequest request) {
        try {
            logger.info("Solicitud POST para crear cliente");
            ClienteDTO cliente = clienteApplicationService.createCliente(request);
            String clienteJson = objectMapper.writeValueAsString(cliente);
            logger.info("Cliente creado exitosamente con ID: {}", cliente.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(true, clienteJson));
        } catch (Exception e) {
            logger.error("Error al crear cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al crear cliente: " + e.getMessage()));
        }
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id El ID del cliente.
     * @return La respuesta genérica con el cliente encontrado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente", description = "Obtiene un cliente por su ID")
    public ResponseEntity<GenericResponse> getClienteById(@PathVariable Long id) {
        try {
            logger.info("Solicitud GET para cliente con ID: {}", id);
            ClienteDTO cliente = clienteApplicationService.getClienteById(id);
            String clienteJson = objectMapper.writeValueAsString(cliente);
            return ResponseEntity.ok(new GenericResponse(true, clienteJson));
        } catch (Exception e) {
            logger.error("Error al obtener cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(false, "Cliente no encontrado: " + e.getMessage()));
        }
    }

    /**
     * Obtiene todos los clientes.
     * @return La respuesta genérica con la lista de clientes.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Obtiene una lista de todos los clientes")
    public ResponseEntity<GenericResponse> getAllClientes() {
        try {
            logger.info("Solicitud GET para todos los clientes");
            List<ClienteDTO> clientes = clienteApplicationService.getAllClientes();
            String clientesJson = objectMapper.writeValueAsString(clientes);
            return ResponseEntity.ok(new GenericResponse(true, clientesJson));
        } catch (Exception e) {
            logger.error("Error al obtener clientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al obtener clientes: " + e.getMessage()));
        }
    }

    /**
     * Actualiza un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return La respuesta genérica con el cliente actualizado.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente")
    public ResponseEntity<GenericResponse> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        try {
            logger.info("Solicitud PUT para actualizar cliente con ID: {}", id);
            ClienteDTO cliente = clienteApplicationService.updateCliente(id, request);
            String clienteJson = objectMapper.writeValueAsString(cliente);
            logger.info("Cliente actualizado exitosamente con ID: {}", cliente.getId());
            return ResponseEntity.ok(new GenericResponse(true, clienteJson));
        } catch (Exception e) {
            logger.error("Error al actualizar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al actualizar cliente: " + e.getMessage()));
        }
    }

    /**
     * Actualiza parcialmente un cliente.
     * @param id El ID del cliente a actualizar.
     * @param request La solicitud con los datos a actualizar.
     * @return La respuesta genérica con el cliente actualizado.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar cliente parcialmente", description = "Actualiza parcialmente un cliente existente")
    public ResponseEntity<GenericResponse> patchCliente(@PathVariable Long id, @RequestBody ClienteRequest request) {
        try {
            logger.info("Solicitud PATCH para cliente con ID: {}", id);
            // Para simplificar, usamos el mismo método de update
            ClienteDTO cliente = clienteApplicationService.updateCliente(id, request);
            String clienteJson = objectMapper.writeValueAsString(cliente);
            logger.info("Cliente actualizado parcialmente con ID: {}", cliente.getId());
            return ResponseEntity.ok(new GenericResponse(true, clienteJson));
        } catch (Exception e) {
            logger.error("Error al actualizar parcialmente cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al actualizar parcialmente cliente: " + e.getMessage()));
        }
    }

    /**
     * Elimina un cliente por su ID.
     * @param id El ID del cliente a eliminar.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    public ResponseEntity<GenericResponse> deleteCliente(@PathVariable Long id) {
        try {
            logger.info("Solicitud DELETE para cliente con ID: {}", id);
            clienteApplicationService.deleteCliente(id);
            logger.info("Cliente eliminado exitosamente con ID: {}", id);
            return ResponseEntity.ok(new GenericResponse(true, "Cliente eliminado exitosamente"));
        } catch (Exception e) {
            logger.error("Error al eliminar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al eliminar cliente: " + e.getMessage()));
        }
    }
}