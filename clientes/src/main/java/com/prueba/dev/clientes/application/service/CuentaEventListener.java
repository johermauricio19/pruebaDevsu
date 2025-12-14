package com.prueba.dev.clientes.application.service;

import com.prueba.dev.clientes.application.dto.event.CuentaCreadaEvent;
import com.prueba.dev.clientes.domain.model.Cliente;
import com.prueba.dev.clientes.domain.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener para eventos de cuentas desde el microservicio de cuentas.
 */
@Component
public class CuentaEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CuentaEventListener.class);

    private final ClienteService clienteService;

    public CuentaEventListener(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Procesa el evento de cuenta creada.
     * @param event El evento recibido.
     */
    @RabbitListener(queues = "cliente.queue")
    public void handleCuentaCreada(CuentaCreadaEvent event) {
        logger.info("Recibido evento de cuenta creada: Cliente ID {}, Cuenta ID {}, Número de cuenta {}",
                event.getClienteId(), event.getCuentaId(), event.getNumeroCuenta());

        // Actualizar el número de cuentas del cliente
        try {
            Cliente cliente = clienteService.getClienteById(event.getClienteId());
            cliente.setNumeroCuentas(cliente.getNumeroCuentas() + 1);
            clienteService.updateCliente(event.getClienteId(), cliente);
            logger.info("Actualizado número de cuentas para cliente ID {}: {}", event.getClienteId(), cliente.getNumeroCuentas());
        } catch (Exception e) {
            logger.error("Error al actualizar el número de cuentas para cliente ID {}: {}", event.getClienteId(), e.getMessage());
        }
    }
}