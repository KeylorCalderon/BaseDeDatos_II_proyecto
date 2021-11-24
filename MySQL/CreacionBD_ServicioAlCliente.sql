CREATE DATABASE ServicioAlCliente;

USE ServicioAlCliente;

CREATE TABLE Agentes(ID INT PRIMARY KEY AUTO_INCREMENT,
					 nombre VARCHAR(50),
					 apellidos VARCHAR(50),
					 telefono INT,
					 correo VARCHAR(50));

CREATE TABLE TipoComentario(ID INT PRIMARY KEY AUTO_INCREMENT,
							tipo VARCHAR(00));

CREATE TABLE Comentarios(ID INT PRIMARY KEY AUTO_INCREMENT,
						 comentario VARCHAR(500),
						 tipoComentarioID INT,
						 agenteID INT,
						 empleadoID INT,
						 FOREIGN KEY (tipoComentarioID) REFERENCES TipoComentario (ID),
						 FOREIGN KEY (agenteID) REFERENCES Agentes (ID));