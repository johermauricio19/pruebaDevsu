-- ============================================================
-- SCHEMA
-- ============================================================
CREATE SCHEMA IF NOT EXISTS pruebadev;
USE pruebadev;

-- ============================================================
-- TABLE: persona
-- ============================================================
DROP TABLE IF EXISTS persona;

CREATE TABLE persona (
    persona_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL CHECK (edad >= 18),
    genero VARCHAR(20) CHECK (genero IN ('Masculino', 'Femenino', 'Otro')),
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(20),

    CONSTRAINT chk_persona_edad CHECK (edad >= 18)
);

-- Índice para búsquedas rápidas por identificación
CREATE INDEX idx_persona_identificacion ON persona (identificacion);


-- ============================================================
-- TABLE: cliente
-- ============================================================
DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    persona_id BIGINT NOT NULL,
    clave VARCHAR(255) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO' 
        CHECK (estado IN ('ACTIVO', 'INACTIVO', 'BLOQUEADO')),
    numero_cuentas INT DEFAULT 0,

    FOREIGN KEY (persona_id) REFERENCES persona(persona_id)
);

-- Índice para mejorar consultas por persona
CREATE INDEX idx_cliente_persona_id ON cliente (persona_id);

-- ============================================================
-- TABLE: cuenta
-- ============================================================
DROP TABLE IF EXISTS cuenta;

CREATE TABLE cuenta (
    cuenta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(50) NOT NULL 
        CHECK (tipo_cuenta IN ('Ahorros', 'Corriente', 'Inversion')),
    saldo_inicial DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVA'
        CHECK (estado IN ('ACTIVA', 'CERRADA', 'SUSPENDIDA')),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

-- Índices recomendados por rendimiento
CREATE INDEX idx_cuenta_cliente_id ON cuenta (cliente_id);
CREATE INDEX idx_cuenta_numero ON cuenta (numero_cuenta);

-- ============================================================
-- TABLE: movimiento
-- ============================================================
DROP TABLE IF EXISTS movimiento;

CREATE TABLE movimiento (
    movimiento_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuenta_id BIGINT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(50) NOT NULL 
        CHECK (tipo_movimiento IN ('DEPOSITO', 'RETIRO', 'TRANSFERENCIA')),
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,

    FOREIGN KEY (cuenta_id) REFERENCES cuenta(cuenta_id)
);

-- Índices optimizados
CREATE INDEX idx_movimiento_cuenta_id ON movimiento (cuenta_id);
CREATE INDEX idx_movimiento_fecha ON movimiento (fecha);
CREATE INDEX idx_movimiento_cuenta_fecha ON movimiento (cuenta_id, fecha);
