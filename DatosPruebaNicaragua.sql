USE [base_Nicaragua]
GO

--Tipos de usuario
INSERT INTO TiposDeUsuario VALUES('Consulta');
INSERT INTO TiposDeUsuario VALUES('Factura');
INSERT INTO TiposDeUsuario VALUES('Administrador');
INSERT INTO TiposDeUsuario VALUES('Cliente');
GO
--Usuarios
INSERT INTO Usuarios VALUES('Morshu','123',3);
INSERT INTO Usuarios VALUES('Beatbox','456',3);
INSERT INTO Usuarios VALUES('Amongus','789',2);
INSERT INTO Usuarios VALUES('Natalie','abc',2);
INSERT INTO Usuarios VALUES('Keylor','xyz',1);
INSERT INTO Usuarios VALUES('Hat','a7671',1);
INSERT INTO Usuarios VALUES('Cliente1','123',4);
INSERT INTO Usuarios VALUES('Cliente2','123',4);
INSERT INTO Usuarios VALUES('Cliente3','123',4);
GO
--Tipos de añejado
INSERT INTO TiposAnejado VALUES('Poco anejo');
INSERT INTO TiposAnejado VALUES('Anejo');
INSERT INTO TiposAnejado VALUES('Muy anejo');
GO
--Paises
INSERT INTO Paises VALUES('Italia');
INSERT INTO Paises VALUES('China');
INSERT INTO Paises VALUES('Francia');
GO
--Licores
INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
SELECT 1,10,1, BulkColumn, 'Vino N'  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Licor1.jpg', Single_Blob) as Imagen;
INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
SELECT 1,20,2, BulkColumn, 'Alcohol N'  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Licor2.jpg', Single_Blob) as Imagen;
INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
SELECT 2,12,3, BulkColumn, 'Agua N'  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Licor3.jpg', Single_Blob) as Imagen;
INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
SELECT 2,3,1, BulkColumn, 'Vino tinto N'  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Licor4.png', Single_Blob) as Imagen;
INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
SELECT 3,1,2, BulkColumn, 'Cerveza N'  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Licor5.jpg', Single_Blob) as Imagen;
INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
SELECT 3,50,3, BulkColumn, 'Cerveza azul N'  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Licor6.jpg', Single_Blob) as Imagen;
GO

--Sucursales
INSERT INTO Sucursales VALUES(1, geography::STGeomFromText('POLYGON((-122.358 47.653, -122.348 47.649, -122.348 47.658, -122.358 47.658, -122.358 47.653))', 4326));
INSERT INTO Sucursales VALUES(2, geography::STGeomFromText('POLYGON((-162.358 47.653, -172.348 47.649, -152.348 47.658, -172.358 47.658, -162.358 47.653))', 4326));
INSERT INTO Sucursales VALUES(3, geography::STGeomFromText('POLYGON((-112.358 47.653, -142.348 47.649, -132.348 47.658, -122.358 47.658, -112.358 47.653))', 4326));
GO
--Paises
INSERT INTO Inventarios VALUES(1,1,24,5000.75);
INSERT INTO Inventarios VALUES(2,2,120,238230.75);
INSERT INTO Inventarios VALUES(3,3,283,129319.50);
INSERT INTO Inventarios VALUES(4,1,732,10000.10);
INSERT INTO Inventarios VALUES(5,2,392,12030.93);
INSERT INTO Inventarios VALUES(6,3,39,102012.10);

INSERT INTO Inventarios VALUES(1,2,24,766.75);
INSERT INTO Inventarios VALUES(2,3,120,276230.75);
INSERT INTO Inventarios VALUES(3,1,230,39319.50);
INSERT INTO Inventarios VALUES(4,2,732,2000.10);
INSERT INTO Inventarios VALUES(5,3,392,3112030.93);
INSERT INTO Inventarios VALUES(6,1,39,102112.10);

INSERT INTO Inventarios VALUES(1,3,234,50870.75);
INSERT INTO Inventarios VALUES(2,1,10,2387230.75);
INSERT INTO Inventarios VALUES(3,2,263,829319.50);
INSERT INTO Inventarios VALUES(4,3,72,100.10);
INSERT INTO Inventarios VALUES(5,1,92,130.93);
INSERT INTO Inventarios VALUES(6,2,3879,1012.10);
GO
--Clientes
INSERT INTO Clientes VALUES('Pepe','Calderón',8291282,'2kskalsk',7,'10-02-2000');
INSERT INTO Clientes VALUES('María','Toruño',5291282,'2aaakskalsk',8,'14-06-1980');
INSERT INTO Clientes VALUES('Don','Ramirez',6391282,'2kskaewelsk',9,'25-11-1995');
GO

--Tipos de pago
INSERT INTO TipoMetodoPago VALUES('Tarjeta de crédito');
INSERT INTO TipoMetodoPago VALUES('Efectivo');
GO

--MetodosDePago
INSERT INTO MetodoPago VALUES(1,1);
INSERT INTO MetodoPago VALUES(1,2);
INSERT INTO MetodoPago VALUES(1,3);
INSERT INTO MetodoPago VALUES(2,1);
INSERT INTO MetodoPago VALUES(2,2);
INSERT INTO MetodoPago VALUES(2,3);
GO

--Empleados
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Juan','Vega',1291282,'9932alsk', 1, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado1.jpg', Single_Blob) as Imagen;
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Jeimy','Mendez',9391282,'jjjdlsk',2, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado2.jpg', Single_Blob) as Imagen;
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Fabian','Sanchez',3391282,'aewelsk',3, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado3.jpg', Single_Blob) as Imagen;

INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Alejandro','Soto',883288,'kdkksdsk', 1, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado3.jpg', Single_Blob) as Imagen;
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Keylin','Arlin',2213311,'jjjdlsk',2, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado1.jpg', Single_Blob) as Imagen;
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Pedro','Sanchez',29932993,'aewelsk',3, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado2.jpg', Single_Blob) as Imagen;

INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Brayan','Navarro',22332991,'dsjfmmsk', 1, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado2.jpg', Single_Blob) as Imagen;
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'José','Acuña',2991222,'dskmmass',2, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado3.jpg', Single_Blob) as Imagen;
INSERT INTO Empleados (nombre, apellidos, telefono, correo, sucursalID, fotografia, usuarioID)  
SELECT 'Eduardo','Lopez',32973,'KassjKAs',3, BulkColumn, 1  FROM Openrowset(Bulk 'C:\Users\1001001222\Documents\BD\Licores\Empleado1.jpg', Single_Blob) as Imagen;
GO

--Facturas
INSERT INTO Facturas VALUES('10-02-2021',1,1,3,0,20120.75,0);
INSERT INTO Facturas VALUES('08-04-2021',1,2,1,0,90120.75,0);
INSERT INTO Facturas VALUES('13-07-2021',2,3,2,0,120120.75,0);
INSERT INTO Facturas VALUES('10-02-2021',3,1,3,0,20120.75,0);
INSERT INTO Facturas VALUES('08-04-2021',2,2,1,0,50120.75,0);
INSERT INTO Facturas VALUES('13-07-2021',1,3,2,0,420120.75,0);
INSERT INTO Facturas VALUES('13-07-2021',2,3,2,0,220120.75,0);
GO

--Detalles
INSERT INTO Detalles VALUES(1,1,4,20120.75);
INSERT INTO Detalles VALUES(2,1,4,5030.18);
INSERT INTO Detalles VALUES(3,2,10,12012.75);
INSERT INTO Detalles VALUES(4,3,4,20120.75);
INSERT INTO Detalles VALUES(5,4,4,5030.18);
INSERT INTO Detalles VALUES(6,5,10,12012.75);
INSERT INTO Detalles VALUES(7,6,10,12012.75);
GO