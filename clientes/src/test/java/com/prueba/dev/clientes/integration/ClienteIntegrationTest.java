package com.prueba.dev.clientes.integration;

import com.prueba.dev.clientes.application.dto.response.GenericResponse;
import com.prueba.dev.clientes.application.dto.PersonaDTO;
import com.prueba.dev.clientes.application.dto.ClienteDTO;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para el microservicio de clientes.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetPersona() throws Exception {
        // Crear persona
        PersonaDTO personaDTO = new PersonaDTO("Juan", 30, "Masculino", "123456789", "Calle 123", "555-1234");

        MvcResult result = mockMvc.perform(post("/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        GenericResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), GenericResponse.class);
        assertTrue(response.isStatus());

        PersonaDTO createdPersona = objectMapper.readValue(response.getMessage(), PersonaDTO.class);
        assertNotNull(createdPersona.getId());
        assertEquals("Juan", createdPersona.getNombre());

        // Obtener persona
        mockMvc.perform(get("/personas/" + createdPersona.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void testCreateAndGetCliente() throws Exception {
        // Crear persona primero
        PersonaDTO personaDTO = new PersonaDTO("Maria", 25, "Femenino", "987654321", "Avenida 456", "555-5678");

        MvcResult personaResult = mockMvc.perform(post("/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        GenericResponse personaResponse = objectMapper.readValue(personaResult.getResponse().getContentAsString(), GenericResponse.class);
        PersonaDTO createdPersona = objectMapper.readValue(personaResponse.getMessage(), PersonaDTO.class);

        // Crear cliente
        ClienteDTO clienteDTO = new ClienteDTO(createdPersona.getId(), "password123", "ACTIVO");

        MvcResult clienteResult = mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        GenericResponse clienteResponse = objectMapper.readValue(clienteResult.getResponse().getContentAsString(), GenericResponse.class);
        assertTrue(clienteResponse.isStatus());

        ClienteDTO createdCliente = objectMapper.readValue(clienteResponse.getMessage(), ClienteDTO.class);
        assertNotNull(createdCliente.getId());
        assertEquals("ACTIVO", createdCliente.getEstado());

        // Obtener cliente
        mockMvc.perform(get("/clientes/" + createdCliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }
}