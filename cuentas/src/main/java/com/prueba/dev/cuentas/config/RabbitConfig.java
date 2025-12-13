package com.prueba.dev.cuentas.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el microservicio de cuentas.
 * Define la cola, exchange y binding para comunicación asíncrona.
 */
@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "cuenta.queue";
    public static final String EXCHANGE_NAME = "cuenta.exchange";
    public static final String ROUTING_KEY = "cuenta.routingkey";

    /**
     * Define la cola para mensajes de cuentas.
     * @return La cola configurada.
     */
    @Bean
    public Queue cuentaQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    /**
     * Define el exchange para mensajes de cuentas.
     * @return El exchange configurado.
     */
    @Bean
    public DirectExchange cuentaExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * Define el binding entre la cola y el exchange.
     * @param cuentaQueue La cola.
     * @param cuentaExchange El exchange.
     * @return El binding configurado.
     */
    @Bean
    public Binding cuentaBinding(Queue cuentaQueue, DirectExchange cuentaExchange) {
        return BindingBuilder.bind(cuentaQueue).to(cuentaExchange).with(ROUTING_KEY);
    }

    /**
     * Configura el RabbitTemplate con convertidor JSON.
     * @param connectionFactory La fábrica de conexiones.
     * @return El RabbitTemplate configurado.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Convertidor de mensajes JSON.
     * @return El convertidor configurado.
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}