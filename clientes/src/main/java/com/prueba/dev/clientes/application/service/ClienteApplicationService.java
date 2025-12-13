package com.prueba.dev.clientes.application.service;

import com.prueba.dev.clientes.application.dto.request.ClienteRequest;
import com.prueba.dev.clientes.application.dto.ClienteDTO;
import com.prueba.dev.clientes.domain.model.Cliente;
import com.prueba.dev.clientes.domain.model.Persona;
import com.prueba.dev.clientes.domain.service.ClienteService;
import com.prueba.dev.clientes.domain.service.PersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para operaciones de clientes.
 * Maneja los casos de uso de la aplicación relacionados con clientes.
 */
@Service
@Transactional
public class ClienteApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteApplicationService.class);

    private final ClienteService clienteService;
    private final PersonaService personaService;

    public ClienteApplicationService(ClienteService clienteService, PersonaService personaService) {
        this.clienteService = clienteService;
        this.personaService = personaService;
    }

    /**
     * Crea un nuevo cliente.
     * @param request La solicitud con los datos del cliente.
     * @return El cliente creado.
     */
    public ClienteDTO createCliente(ClienteRequest request) {
        logger.info("Creando cliente para persona ID: {}", request.getPersonaId());

        Persona persona = personaService.getPersonaById(request.getPersonaId());
        Cliente cliente = new Cliente(persona, request.getContraseña(), request.getEstado());

        Cliente savedCliente = clienteService.createCliente(cliente);
        logger.info("Cliente creado exitosamente con ID: {}", savedCliente.getId());

        return new ClienteDTO(
                savedCliente.getId(),
                savedCliente.getPersona().getNombre(),
                savedCliente.getPersona().getGenero(),
                savedCliente.getPersona().getEdad(),
                savedCliente.getPersona().getIdentificacion(),
                savedCliente.getPersona().getDireccion(),
                savedCliente.getPersona().getTelefono(),
                savedCliente.getPersona().getFechaNacimiento(),
                savedCliente.getPersona().getId(),
                savedCliente.getContraseña(),
                savedCliente.getEstado()
        );
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id El ID del cliente.
     * @return El cliente encontrado.
     */
    @Transactional(readOnly = true)
    public ClienteDTO getClienteById(Long id) {
        logger.info("Obteniendo cliente con ID: {}", id);
        Cliente cliente = clienteService.getClienteById(id);
        return new ClienteDTO(
                cliente.getId(),
                cliente.getPersona().getNombre(),
                cliente.getPersona().getGenero(),
                cliente.getPersona().getEdad(),
                cliente.getPersona().getIdentificacion(),
                cliente.getPersona().getDireccion(),
                cliente.getPersona().getTelefono(),
                cliente.getPersona().getFechaNacimiento(),
                cliente.getPersona().getId(),
                cliente.getContraseña(),
                cliente.getEstado()
        );
    }

    /**
     * Obtiene todos los clientes.
     * @return Lista de clientes.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> getAllClientes() {
        logger.info("Obteniendo todos los clientes");
        List<Cliente> clientes = clienteService.getAllClientes();
        return clientes.stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
                        cliente.getPersona().getNombre(),
                        cliente.getPersona().getGenero(),
                        cliente.getPersona().getEdad(),
                        cliente.getPersona().getIdentificacion(),
                        cliente.getPersona().getDireccion(),
                        cliente.getPersona().getTelefono(),
                        cliente.getPersona().getFechaNacimiento(),
                        cliente.getPersona().getId(),
                        cliente.getContraseña(),
                        cliente.getEstado()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param request La solicitud con los nuevos datos.
     * @return El cliente actualizado.
     */
    public ClienteDTO updateCliente(Long id, ClienteRequest request) {
        logger.info("Actualizando cliente con ID: {}", id);

        Persona persona = personaService.getPersonaById(request.getPersonaId());
        Cliente cliente = new Cliente(persona, request.getContraseña(), request.getEstado());

        Cliente updatedCliente = clienteService.updateCliente(id, cliente);
        logger.info("Cliente actualizado exitosamente con ID: {}", updatedCliente.getId());

        return new ClienteDTO(
                updatedCliente.getId(),
                updatedCliente.getPersona().getNombre(),
                updatedCliente.getPersona().getGenero(),
                updatedCliente.getPersona().getEdad(),
                updatedCliente.getPersona().getIdentificacion(),
                updatedCliente.getPersona().getDireccion(),
                updatedCliente.getPersona().getTelefono(),
                updatedCliente.getPersona().getFechaNacimiento(),
                updatedCliente.getPersona().getId(),
                updatedCliente.getContraseña(),
                updatedCliente.getEstado()
        );
    }

    /**
     * Elimina un cliente por su ID.
     * @param id El ID del cliente a eliminar.
     */
    public void deleteCliente(Long id) {
        logger.info("Eliminando cliente con ID: {}", id);
        clienteService.deleteCliente(id);
        logger.info("Cliente eliminado exitosamente con ID: {}", id);
    }
}