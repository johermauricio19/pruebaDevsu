package com.prueba.dev.clientes.infrastructure.persistence.adapter;

import com.prueba.dev.clientes.domain.model.Cliente;
import com.prueba.dev.clientes.domain.port.ClienteRepositoryPort;
import com.prueba.dev.clientes.infrastructure.persistence.jpa.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador para el repositorio de clientes.
 * Implementa el puerto de repositorio utilizando JPA.
 */
@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(ClienteRepositoryAdapter.class);

    private final ClienteRepository clienteRepository;

    public ClienteRepositoryAdapter(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        logger.debug("Guardando cliente con ID: {}", cliente.getId());
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        logger.debug("Buscando cliente con ID: {}", id);
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findAll() {
        logger.debug("Obteniendo todos los clientes");
        return clienteRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("Eliminando cliente con ID: {}", id);
        clienteRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        logger.debug("Verificando existencia de cliente con ID: {}", id);
        return clienteRepository.existsById(id);
    }
}