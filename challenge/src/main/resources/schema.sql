CREATE TABLE IF NOT EXISTS transacciones (
    id VARCHAR(36) PRIMARY KEY,
    cuenta_id VARCHAR(50) NOT NULL,
    tipo_transaccion VARCHAR(10) NOT NULL,
    monto DECIMAL(17, 2) NOT NULL,
    moneda VARCHAR(3) NOT NULL,
    descripcion VARCHAR(50),
    estado VARCHAR(20) NOT NULL,
    transaccion_proveedor_id VARCHAR(100),
    saldo_posterior DECIMAL(17, 2),
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_transacciones_cuenta_fecha_creacion
    ON transacciones (cuenta_id, fecha_creacion DESC);

CREATE INDEX IF NOT EXISTS idx_transacciones_transaccion_proveedor_id
    ON transacciones (transaccion_proveedor_id);