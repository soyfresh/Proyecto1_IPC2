CREATE SCHEMA AppWeb_Buses;
USE AppWeb_Buses;

CREATE TABLE cuenta(
	dpi VARCHAR(13) PRIMARY KEY,
    nit VARCHAR(13),
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150),
    saldo DECIMAL(10,2) DEFAULT 0.00,
    tipo ENUM('CLIENTE', 'ADMINISTRADOR_SISTEMA', 'ADMINISTRADOR_SUCURSAL', 'CHOFER') NOT NULL,
    activo BOOLEAN DEFAULT TRUE
); 

CREATE TABLE registro_recarga(
	id_recarga INT AUTO_INCREMENT,
    dpi VARCHAR(20) NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha_recarga DATE NOT NULL,
    PRIMARY KEY (id_recarga, dpi),
    FOREIGN KEY (dpi) REFERENCES cuenta(dpi) ON UPDATE CASCADE
);

CREATE TABLE cliente (
    dpi VARCHAR(13) PRIMARY KEY,
    FOREIGN KEY (dpi) REFERENCES cuenta(dpi) ON UPDATE CASCADE
);

CREATE TABLE administrador_sistema (
    dpi VARCHAR(13) PRIMARY KEY,
    FOREIGN KEY (dpi) REFERENCES cuenta(dpi) ON UPDATE CASCADE
);

CREATE TABLE configuracion (
    id_configuracion INT AUTO_INCREMENT PRIMARY KEY,
    monto_por_km DECIMAL(8,2) NOT NULL
);
 
 CREATE TABLE administrador_sucursal (
    dpi VARCHAR(13) PRIMARY KEY,
    FOREIGN KEY (dpi) REFERENCES cuenta(dpi) ON UPDATE CASCADE
);

CREATE TABLE sucursal (
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    dpi VARCHAR(13),
    departamento_sucursal VARCHAR(100) NOT NULL,
    direccion_de_la_sucursal VARCHAR(200) NOT NULL,
    FOREIGN KEY (dpi) REFERENCES administrador_sucursal(dpi) ON UPDATE CASCADE
);

CREATE TABLE chofer (
    dpi VARCHAR(13) PRIMARY KEY,
    numero_de_licencia VARCHAR(30) NOT NULL,
    tipo_de_licencia ENUM('A', 'B', 'C') NOT NULL,
    fecha_de_vencimiento_licencia DATE NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    foto MEDIUMBLOB NOT NULL,
    id_sucursal INT NOT NULL,
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal) ON UPDATE CASCADE,
    FOREIGN KEY (dpi) REFERENCES cuenta(dpi) ON UPDATE CASCADE
);

CREATE TABLE bus (
    numero_de_placa VARCHAR(15) PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    ano_de_fabricacion INT NOT NULL,
    capacidad INT NOT NULL,
    kilometraje_actual INT NOT NULL,
    foto  MEDIUMBLOB NOT NULL,
    estado ENUM('DISPONIBLE', 'EN_TRANSITO', 'NO_DISPONIBLE', 'PROGRAMADO') DEFAULT 'DISPONIBLE',
    activo BOOLEAN DEFAULT TRUE,
    id_sucursal INT NOT NULL,
    id_sucursal_actual INT NOT NULL,
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal) ON UPDATE CASCADE
);

CREATE TABLE registro_taller(
	id_registro INT AUTO_INCREMENT PRIMARY KEY,
    numero_de_placa VARCHAR(15) NOT NULL,
	monto_mano_de_obra DECIMAL(10,2) NOT NULL,
    monto_repuestos DECIMAL(10,2) NOT NULL,
    fecha_mantenimiento DATE NOT NULL,
    FOREIGN KEY (numero_de_placa) REFERENCES bus(numero_de_placa) ON UPDATE CASCADE
);

 CREATE TABLE viaje(
	id_viaje INT AUTO_INCREMENT PRIMARY KEY ,
    tipo ENUM('REGULAR', 'PRIVADO') NOT NULL,
    estado_viaje ENUM('PENDIENTE','CONFIRMADO', 'RECHAZADO', 
    'INICIADO', 'TERMINADO', 'PROGRAMADO', 'CANCELADO') NULL,
    kilometraje_actual_bus INT NULL,
    hora_real_salida TIME NULL,
    hora_real_llegada TIME NULL,
    kilometraje_final_bus INT NULL,
    total_de_gasto_combustible DECIMAL(10,2) NULL,
    numero_de_placa VARCHAR(15) NULL,
    dpi_chofer VARCHAR(13) NULL,
    id_sucursal INT NOT NULL,
    FOREIGN KEY (numero_de_placa) REFERENCES bus(numero_de_placa),
    FOREIGN KEY (dpi_chofer) REFERENCES chofer(dpi),
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
 );
 
 CREATE TABLE ruta(
	id_ruta INT AUTO_INCREMENT PRIMARY KEY,
    distancia_aproximada INT NOT NULL,
    precio_de_asiento DECIMAL(8,2) NOT NULL,
    id_sucursal_origen INT NOT NULL,
    id_sucursal_destino INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    descripcion_ruta VARCHAR(150) NOT NULL,
    FOREIGN KEY (id_sucursal_origen) REFERENCES sucursal(id_sucursal) ON UPDATE CASCADE,
    FOREIGN KEY (id_sucursal_destino) REFERENCES sucursal(id_sucursal) ON UPDATE CASCADE
 );

CREATE TABLE viaje_regular (
    id_viaje INT PRIMARY KEY,
    fecha_y_hora_de_salida DATETIME NOT NULL,
    fecha_y_hora_estimada_de_llegada DATETIME NOT NULL,
    id_ruta INT NOT NULL,
    FOREIGN KEY (id_viaje) REFERENCES viaje(id_viaje) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_ruta) REFERENCES ruta(id_ruta) ON UPDATE CASCADE
);

CREATE TABLE viaje_privado (
    id_viaje INT PRIMARY KEY,
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    departamentoOrigen VARCHAR(100) NOT NULL,
    departamentoDestino VARCHAR(100) NOT NULL,
    fecha_de_salida DATE NOT NULL,
    fecha_de_retorno DATE NULL, #queda null porque si se agrega un dato aqui significa ida y vuelta
    numero_de_pasajeros INT NOT NULL,
    precio_estimado DECIMAL(10,2) NOT NULL,
    precio_final DECIMAL(10,2) NULL,
    fecha_de_pago DATE NULL,
    dpi_cliente VARCHAR(13) NOT NULL,
    FOREIGN KEY (id_viaje) REFERENCES viaje(id_viaje) ON UPDATE CASCADE,
    FOREIGN KEY (dpi_cliente) REFERENCES cliente(dpi) ON UPDATE CASCADE
);

CREATE TABLE boleto (
    id_boleto INT AUTO_INCREMENT PRIMARY KEY,
    total DECIMAL(10,2) NOT NULL,
    fecha_de_compra DATE NULL,
    id_viaje INT NOT NULL,
    dpi_cliente VARCHAR(13) NOT NULL,
    FOREIGN KEY (id_viaje) REFERENCES viaje_regular(id_viaje) ON UPDATE CASCADE,
    FOREIGN KEY (dpi_cliente) REFERENCES cliente(dpi) ON UPDATE CASCADE
);

CREATE TABLE detalle_boleto (
    id_boleto INT NOT NULL,
    numero_de_asiento_ocupado INT NOT NULL,
    PRIMARY KEY (id_boleto, numero_de_asiento_ocupado),
    FOREIGN KEY (id_boleto) REFERENCES boleto(id_boleto) ON DELETE CASCADE ON UPDATE CASCADE
);
