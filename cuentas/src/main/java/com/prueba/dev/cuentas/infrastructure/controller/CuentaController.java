package com.prueba.dev.cuentas.infrastructure.controller;

import com.prueba.dev.cuentas.application.dto.request.CuentaRequest;
import com.prueba.dev.cuentas.application.dto.GenericResponse;
import com.prueba.dev.cuentas.application.dto.CuentaDTO;
import com.prueba.dev.cuentas.application.dto.EstadoCuentaDTO;
import com.prueba.dev.cuentas.application.dto.MovimientoDTO;
import com.prueba.dev.cuentas.application.service.CuentaApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para operaciones de cuentas.
 * Proporciona endpoints para CRUD de cuentas y operaciones bancarias.
 */
@RestController
@RequestMapping("/cuentas")
@Tag(name = "Cuentas", description = "API para gestión de cuentas bancarias")
public class CuentaController {

    private static final Logger logger = LoggerFactory.getLogger(CuentaController.class);

    private final CuentaApplicationService cuentaApplicationService;
    private final ObjectMapper objectMapper;

    public CuentaController(CuentaApplicationService cuentaApplicationService, ObjectMapper objectMapper) {
        this.cuentaApplicationService = cuentaApplicationService;
        this.objectMapper = objectMapper;
    }

    /**
     * Crea una nueva cuenta.
     * @param request La solicitud con los datos de la cuenta.
     * @return La respuesta genérica con el resultado.
     */
    @PostMapping
    @Operation(summary = "Crear cuenta", description = "Crea una nueva cuenta bancaria")
    public ResponseEntity<GenericResponse> createCuenta(@Valid @RequestBody CuentaRequest request) {
        try {
            logger.info("Solicitud POST para crear cuenta");
            CuentaDTO cuenta = cuentaApplicationService.createCuenta(request);
            String cuentaJson = objectMapper.writeValueAsString(cuenta);
            logger.info("Cuenta creada exitosamente con ID: {}", cuenta.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(true, cuentaJson));
        } catch (Exception e) {
            logger.error("Error al crear cuenta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al crear cuenta: " + e.getMessage()));
        }
    }

    /**
     * Obtiene una cuenta por su ID.
     * @param id El ID de la cuenta.
     * @return La respuesta genérica con la cuenta encontrada.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta", description = "Obtiene una cuenta por su ID")
    public ResponseEntity<GenericResponse> getCuentaById(@PathVariable Long id) {
        try {
            logger.info("Solicitud GET para cuenta con ID: {}", id);
            CuentaDTO cuenta = cuentaApplicationService.getCuentaById(id);
            String cuentaJson = objectMapper.writeValueAsString(cuenta);
            return ResponseEntity.ok(new GenericResponse(true, cuentaJson));
        } catch (Exception e) {
            logger.error("Error al obtener cuenta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse(false, "Cuenta no encontrada: " + e.getMessage()));
        }
    }

    /**
     * Obtiene todas las cuentas.
     * @return La respuesta genérica con la lista de cuentas.
     */
    @GetMapping
    @Operation(summary = "Obtener todas las cuentas", description = "Obtiene una lista de todas las cuentas")
    public ResponseEntity<GenericResponse> getAllCuentas() {
        try {
            logger.info("Solicitud GET para todas las cuentas");
            List<CuentaDTO> cuentas = cuentaApplicationService.getAllCuentas();
            String cuentasJson = objectMapper.writeValueAsString(cuentas);
            return ResponseEntity.ok(new GenericResponse(true, cuentasJson));
        } catch (Exception e) {
            logger.error("Error al obtener cuentas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse(false, "Error al obtener cuentas: " + e.getMessage()));
        }
    }

    /**
     * Obtiene cuentas por cliente ID.
     * @param clienteId El ID del cliente.
     * @return Lista de cuentas del cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener cuentas por cliente", description = "Obtiene las cuentas de un cliente específico")
    public ResponseEntity<GenericResponse> getCuentasByClienteId(@PathVariable Long clienteId) {
        logger.info("Solicitud GET para cuentas de cliente ID: {}", clienteId);
        try {
            List<CuentaDTO> cuentas = cuentaApplicationService.getCuentasByClienteId(clienteId);
            String jsonData = objectMapper.writeValueAsString(cuentas);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener cuentas del cliente: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al obtener cuentas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Actualiza una cuenta existente.
     * @param id El ID de la cuenta a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return La respuesta con la cuenta actualizada.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cuenta", description = "Actualiza una cuenta existente")
    public ResponseEntity<GenericResponse> updateCuenta(@PathVariable Long id, @Valid @RequestBody CuentaRequest request) {
        logger.info("Solicitud PUT para actualizar cuenta con ID: {}", id);
        try {
            CuentaDTO cuenta = cuentaApplicationService.updateCuenta(id, request);
            String jsonData = objectMapper.writeValueAsString(cuenta);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al actualizar cuenta: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al actualizar cuenta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Actualiza parcialmente una cuenta.
     * @param id El ID de la cuenta a actualizar.
     * @param request La solicitud con los datos a actualizar.
     * @return La respuesta con la cuenta actualizada.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar cuenta parcialmente", description = "Actualiza parcialmente una cuenta existente")
    public ResponseEntity<GenericResponse> patchCuenta(@PathVariable Long id, @RequestBody CuentaRequest request) {
        logger.info("Solicitud PATCH para cuenta con ID: {}", id);
        try {
            CuentaDTO cuenta = cuentaApplicationService.updateCuenta(id, request);
            String jsonData = objectMapper.writeValueAsString(cuenta);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al actualizar cuenta parcialmente: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al actualizar cuenta parcialmente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Elimina una cuenta por su ID.
     * @param id El ID de la cuenta a eliminar.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cuenta", description = "Elimina una cuenta por su ID")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        logger.info("Solicitud DELETE para cuenta con ID: {}", id);
        cuentaApplicationService.deleteCuenta(id);
        logger.info("Cuenta eliminada exitosamente con ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza un depósito en una cuenta.
     * @param cuentaId El ID de la cuenta.
     * @param valor El valor a depositar.
     * @return El movimiento creado.
     */
    @PostMapping("/{cuentaId}/deposito")
    @Operation(summary = "Depositar en cuenta", description = "Realiza un depósito en la cuenta especificada")
    public ResponseEntity<GenericResponse> depositar(@PathVariable Long cuentaId, @RequestParam BigDecimal valor) {
        logger.info("Solicitud POST para depósito en cuenta ID: {} con valor: {}", cuentaId, valor);
        try {
            MovimientoDTO movimiento = cuentaApplicationService.depositar(cuentaId, valor);
            String jsonData = objectMapper.writeValueAsString(movimiento);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error al realizar depósito: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al realizar depósito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Realiza un retiro de una cuenta.
     * @param cuentaId El ID de la cuenta.
     * @param valor El valor a retirar.
     * @return El movimiento creado.
     */
    @PostMapping("/{cuentaId}/retiro")
    @Operation(summary = "Retirar de cuenta", description = "Realiza un retiro de la cuenta especificada")
    public ResponseEntity<GenericResponse> retirar(@PathVariable Long cuentaId, @RequestParam BigDecimal valor) {
        logger.info("Solicitud POST para retiro en cuenta ID: {} con valor: {}", cuentaId, valor);
        try {
            MovimientoDTO movimiento = cuentaApplicationService.retirar(cuentaId, valor);
            String jsonData = objectMapper.writeValueAsString(movimiento);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error al realizar retiro: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al realizar retiro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Genera el estado de cuenta para un cliente en un rango de fechas.
     * @param clienteId El ID del cliente.
     * @param fechaInicio Fecha de inicio.
     * @param fechaFin Fecha de fin.
     * @return El estado de cuenta en JSON.
     */
    @GetMapping("/reportes")
    @Operation(summary = "Estado de cuenta", description = "Genera el reporte de estado de cuenta para un cliente en un rango de fechas")
    public ResponseEntity<GenericResponse> getEstadoCuenta(@RequestParam Long clienteId,
                                                           @RequestParam LocalDate fechaInicio,
                                                           @RequestParam LocalDate fechaFin) {
        logger.info("Solicitud GET para estado de cuenta cliente ID: {} entre {} y {}", clienteId, fechaInicio, fechaFin);
        try {
            EstadoCuentaDTO estadoCuenta = cuentaApplicationService.getEstadoCuenta(clienteId, fechaInicio, fechaFin);
            String jsonData = objectMapper.writeValueAsString(estadoCuenta);
            GenericResponse response = new GenericResponse(true, jsonData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al generar estado de cuenta: {}", e.getMessage());
            GenericResponse response = new GenericResponse(false, "Error al generar estado de cuenta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}