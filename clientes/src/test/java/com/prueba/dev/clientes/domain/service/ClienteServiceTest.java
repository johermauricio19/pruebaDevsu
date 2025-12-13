package com.prueba.dev.clientes.domain.service;

import com.prueba.dev.clientes.domain.exception.ClienteNotFoundException;
import com.prueba.dev.clientes.domain.model.Cliente;
import com.prueba.dev.clientes.domain.model.Persona;
import com.prueba.dev.clientes.domain.port.ClienteRepositoryPort;
import com.prueba.dev.clientes.domain.port.PersonaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepositoryPort;

    @Mock
    private PersonaRepositoryPort personaRepositoryPort;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private Persona persona;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        persona = new Persona("Juan", 30, "Masculino", "123456789", "Calle 123", "555-1234", LocalDate.of(1990, 1, 1));
        persona.setId(1L);
        cliente = new Cliente(persona, "password123", "ACTIVO");
        cliente.setId(1L);
        when(personaRepositoryPort.existsById(1L)).thenReturn(true);
    }

    @Test
    void createCliente_ShouldReturnSavedCliente() {
        when(clienteRepositoryPort.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.createCliente(cliente);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        verify(clienteRepositoryPort, times(1)).save(cliente);
    }

    @Test
    void getClienteById_ShouldReturnCliente_WhenExists() {
        when(clienteRepositoryPort.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteById(1L);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        verify(clienteRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getClienteById_ShouldThrowException_WhenNotExists() {
        when(clienteRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> clienteService.getClienteById(1L));
        verify(clienteRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getAllClientes_ShouldReturnListOfClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepositoryPort.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.getAllClientes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clienteRepositoryPort, times(1)).findAll();
    }

    @Test
    void updateCliente_ShouldReturnUpdatedCliente() {
        when(clienteRepositoryPort.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepositoryPort.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updatedCliente = new Cliente(persona, "newpassword", "INACTIVO");
        Cliente result = clienteService.updateCliente(1L, updatedCliente);

        assertNotNull(result);
        verify(clienteRepositoryPort, times(1)).findById(1L);
        verify(clienteRepositoryPort, times(1)).save(any(Cliente.class));
    }

    @Test
    void deleteCliente_ShouldCallRepositoryDelete() {
        when(clienteRepositoryPort.existsById(1L)).thenReturn(true);

        clienteService.deleteCliente(1L);

        when(clienteRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        verify(clienteRepositoryPort, times(1)).existsById(1L);
        verify(clienteRepositoryPort, times(1)).deleteById(1L);
        verify(personaRepositoryPort, times(1)).deleteById(1L);
        assertThrows(ClienteNotFoundException.class, () -> clienteService.getClienteById(1L));
    }
}