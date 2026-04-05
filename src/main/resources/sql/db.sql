
USE DonAlfonso;
CREATE TABLE productos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(255),
    tipo VARCHAR(50),
    stock VARCHAR(50),
    estado CHAR(1),

    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    restored_at DATETIME
);

SELECT * FROM productos;