package com.prueba.dev.clientes.infrastructure.persistence.jpa;

import com.prueba.dev.clientes.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Cliente.
 * Proporciona operaciones CRUD básicas y personalizadas.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos personalizados si son necesarios
}