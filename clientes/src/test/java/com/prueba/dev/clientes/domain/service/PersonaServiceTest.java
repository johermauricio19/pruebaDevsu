package com.prueba.dev.clientes.domain.service;

import com.prueba.dev.clientes.domain.exception.PersonaNotFoundException;
import com.prueba.dev.clientes.domain.model.Persona;
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

class PersonaServiceTest {

    @Mock
    private PersonaRepositoryPort personaRepositoryPort;

    @InjectMocks
    private PersonaService personaService;

    private Persona persona;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        persona = new Persona("Juan", 30, "Masculino", "123456789", "Calle 123", "555-1234", LocalDate.of(1990, 1, 1));
        persona.setId(1L);
    }

    @Test
    void createPersona_ShouldReturnSavedPersona() {
        when(personaRepositoryPort.save(any(Persona.class))).thenReturn(persona);

        Persona result = personaService.createPersona(persona);

        assertNotNull(result);
        assertEquals(persona.getId(), result.getId());
        verify(personaRepositoryPort, times(1)).save(persona);
    }

    @Test
    void getPersonaById_ShouldReturnPersona_WhenExists() {
        when(personaRepositoryPort.findById(1L)).thenReturn(Optional.of(persona));

        Persona result = personaService.getPersonaById(1L);

        assertNotNull(result);
        assertEquals(persona.getId(), result.getId());
        verify(personaRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getPersonaById_ShouldThrowException_WhenNotExists() {
        when(personaRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonaNotFoundException.class, () -> personaService.getPersonaById(1L));
        verify(personaRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getAllPersonas_ShouldReturnListOfPersonas() {
        List<Persona> personas = Arrays.asList(persona);
        when(personaRepositoryPort.findAll()).thenReturn(personas);

        List<Persona> result = personaService.getAllPersonas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(personaRepositoryPort, times(1)).findAll();
    }

    @Test
    void updatePersona_ShouldReturnUpdatedPersona() {
        when(personaRepositoryPort.findById(1L)).thenReturn(Optional.of(persona));
        when(personaRepositoryPort.save(any(Persona.class))).thenReturn(persona);

        Persona updatedPersona = new Persona("Juan Updated", 31, "Masculino", "123456789", "Calle 456", "555-5678", LocalDate.of(1990, 1, 1));
        Persona result = personaService.updatePersona(1L, updatedPersona);

        assertNotNull(result);
        verify(personaRepositoryPort, times(1)).findById(1L);
        verify(personaRepositoryPort, times(1)).save(any(Persona.class));
    }

    @Test
    void deletePersona_ShouldCallRepositoryDelete() {
        when(personaRepositoryPort.existsById(1L)).thenReturn(true);

        personaService.deletePersona(1L);

        when(personaRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        verify(personaRepositoryPort, times(1)).existsById(1L);
        verify(personaRepositoryPort, times(1)).deleteById(1L);
        assertThrows(PersonaNotFoundException.class, () -> personaService.getPersonaById(1L));
    }
}