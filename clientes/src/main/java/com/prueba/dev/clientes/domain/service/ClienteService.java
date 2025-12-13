package com.prueba.dev.clientes.domain.service;

import com.prueba.dev.clientes.domain.exception.ClienteNotFoundException;
import com.prueba.dev.clientes.domain.exception.PersonaNotFoundException;
import com.prueba.dev.clientes.domain.model.Cliente;
import com.prueba.dev.clientes.domain.model.Persona;
import com.prueba.dev.clientes.domain.port.ClienteRepositoryPort;
import com.prueba.dev.clientes.domain.port.PersonaRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de dominio para operaciones de clientes.
 * Contiene la lógica de negocio relacionada con clientes.
 */
@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepositoryPort clienteRepository;
    private final PersonaRepositoryPort personaRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository, PersonaRepositoryPort personaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
    }

    /**
     * Crea un nuevo cliente.
     * @param cliente El cliente a crear.
     * @return El cliente creado.
     */
    public Cliente createCliente(Cliente cliente) {
        logger.info("Creando cliente para persona ID: {}", cliente.getPersona().getId());

        // Verificar que la persona existe
        if (!personaRepository.existsById(cliente.getPersona().getId())) {
            throw new PersonaNotFoundException("Persona no encontrada con ID: " + cliente.getPersona().getId());
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        logger.info("Cliente creado con ID: {}", savedCliente.getId());
        return savedCliente;
    }

    /**
     * Obtiene un cliente por su ID.
     * @param id El ID del cliente.
     * @return El cliente encontrado.
     */
    public Cliente getClienteById(Long id) {
        logger.info("Buscando cliente con ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
    }

    /**
     * Obtiene todos los clientes.
     * @return Lista de clientes.
     */
    public List<Cliente> getAllClientes() {
        logger.info("Obteniendo todos los clientes");
        return clienteRepository.findAll();
    }

    /**
     * Actualiza un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param cliente Los nuevos datos del cliente.
     * @return El cliente actualizado.
     */
    public Cliente updateCliente(Long id, Cliente cliente) {
        logger.info("Actualizando cliente con ID: {}", id);

        Cliente existingCliente = getClienteById(id);
        existingCliente.setContraseña(cliente.getContraseña());
        existingCliente.setEstado(cliente.getEstado());

        Cliente updatedCliente = clienteRepository.save(existingCliente);
        logger.info("Cliente actualizado con ID: {}", updatedCliente.getId());
        return updatedCliente;
    }

    /**
     * Elimina un cliente por su ID.
     * @param id El ID del cliente a eliminar.
     */
    public void deleteCliente(Long id) {
        logger.info("Eliminando cliente con ID: {}", id);

        if (!clienteRepository.existsById(id)) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        }

        clienteRepository.deleteById(id);
        personaRepository.deleteById(id);
        logger.info("Cliente eliminado con ID: {}", id);
    }
}