USE [base_CostaRica]
GO

CREATE PROCEDURE Facturacion
@inID INT

AS
BEGIN
	BEGIN TRY
		DECLARE
			@idEmpleado INT,
			@IdFactura INT;

		SELECT TOP 1
			@idEmpleado=E.ID
		FROM
			Empleados E, FacturaTemporal FT
		WHERE
			E.sucursalID=FT.sucursalID AND FT.Id=3;

		BEGIN TRANSACTION facturar

		INSERT INTO Facturas (fecha, clienteID, empleadoID, sucursalID, envio, total)  
		SELECT
			FT.fecha, FT.clienteID, @idEmpleado AS empleadoID,
			FT.sucursalID, FT.envio, (FT.subtotal+FT.subtotal*0.10) AS total
		FROM
			FacturaTemporal FT
		WHERE
			FT.Id=@inID;

		SET @IdFactura=SCOPE_IDENTITY();

		INSERT INTO Detalles (facturaID, licorID, cantidad, subtotal)  
		SELECT
			@IdFactura AS facturaID, FT.licorID,FT.cantidad, FT.subtotal
		FROM
			FacturaTemporal FT
		WHERE
			FT.Id=@inID;

		UPDATE
			FacturaTemporal
		SET
			pendiente = 1
		WHERE
			FacturaTemporal.ID=@inID;

		COMMIT TRANSACTION facturar
	END TRY
	BEGIN CATCH
		IF @@Trancount>0 
			ROLLBACK TRANSACTION facturar;
	END CATCH
END
GO

EXEC Facturacion 3
GO

CREATE PROCEDURE getCant
@inBebida varchar(50),
@inSucursal INT
AS
BEGIN
	SELECT TOP 1
		I.cantidad
	FROM
		Licores L, Sucursales S, Inventarios I
	WHERE
		L.nombre=@inBebida AND L.ID=I.licorID AND I.sucursalID=S.ID AND S.ID=@inSucursal;

END
GO

EXEC getCant 'Vino',3
GO

CREATE PROCEDURE getFacturasPendientes
AS
BEGIN
	SELECT
		L.ID, L.fecha, C.nombre, L.sucursalID, L.envio, Li.nombre, L.cantidad, L.subtotal, TMP.Nombre
	FROM
		FacturaTemporal L, Clientes C, TipoMetodoPago TMP, Licores Li
	WHERE
		C.ID=L.clienteID AND TMP.ID=L.metodoDePago AND L.licorID=Li.ID AND L.pendiente=0;

END
GO

EXEC getFacturasPendientes
GO

CREATE PROCEDURE insertarFacturaTemp
@inCliente varchar(50),
@inSucursalID int,
@inEnvio bit,
@inLicor varchar(50),
@inCantidad int,
@inPrecio float,
@inMetodoPago int

AS
BEGIN
	DECLARE @Fecha DATE,
			@ClienteID INT,
			@LicorID INT;
	SET @Fecha=GETDATE();

	SELECT TOP 1 @LicorID=L.ID
	FROM Licores L
	WHERE L.nombre=@inLicor;

	SELECT TOP 1 @ClienteID=L.ID
	FROM Clientes L, Usuarios U
	WHERE U.username=@inCliente AND L.usuarioID=U.ID;

	INSERT INTO FacturaTemporal (fecha, clienteID, sucursalID, envio, licorID, cantidad, subtotal,
								metodoDePago, pendiente)  
	VALUES(@Fecha, @ClienteID, @inSucursalID, @inEnvio, @LicorID, @inCantidad, @inPrecio*@inCantidad, 
			@inMetodoPago, 0);
	--SET @IdLicor=SCOPE_IDENTITY();

END
GO

--EXEC insertarProducto 'Cerveza', 12031.93
--GO

CREATE PROCEDURE getProductosSimple
AS
BEGIN
	SELECT
		L.nombre
	FROM
		Licores L;

END
GO

EXEC getProductosSimple
GO

CREATE PROCEDURE getProductosConsultaPorSucursal
@inProducto varchar(50)

AS
BEGIN
	SELECT
		L.nombre, L.fotografia, S.ID AS Sucursal, I.precio, S.ubicacion.MakeValid().STDistance('POINT(0 51.5)') AS Distancia
	FROM
		Licores L, Paises P, TiposAnejado TA, Sucursales S, Inventarios I
	WHERE
		P.ID=L.procedenciaID AND TA.ID=L.tipoAnejadoID AND L.ID=I.licorID
		AND I.sucursalID=S.ID AND @inProducto=L.nombre
	ORDER BY Distancia;

END
GO

EXEC getProductosConsultaPorSucursal 'Vino'
GO

CREATE PROCEDURE insertarProducto
@inPais varchar(50),
@inAnos int,
@inAnejado varchar(50),
@inFoto varbinary(max),
@inSucursal int,
@inCant int,
@inPrecio float,
@inNombre varchar(50)

AS
BEGIN
	DECLARE @IDPais INT, @IDAnejado INT, @IdLicor INT;
	SELECT TOP 1 @IDPais=L.ID
	FROM Paises L
	WHERE L.pais=@inPais;

	SELECT TOP 1 @IDAnejado=L.ID
	FROM TiposAnejado L
	WHERE L.tipo=@inAnejado;

	INSERT INTO Licores (procedenciaID, years, tipoAnejadoID, fotografia, nombre)  
	VALUES(@IDPais, @inAnos, @IDAnejado, @inFoto, @inNombre);
	SET @IdLicor=SCOPE_IDENTITY();

	INSERT INTO Inventarios (licorID, sucursalID, cantidad, precio)  
	VALUES(@IdLicor, @inSucursal, @inCant, @inPrecio);

END
GO

--EXEC insertarProducto 'Cerveza', 12031.93
--GO

CREATE PROCEDURE getSucursal

AS
BEGIN
	SELECT P.ID FROM Sucursales P;
END
GO

EXEC getSucursal
GO

CREATE PROCEDURE getAnejado

AS
BEGIN
	SELECT P.tipo FROM TiposAnejado P;
END
GO

EXEC getAnejado
GO

CREATE PROCEDURE getPaises

AS
BEGIN
	SELECT P.pais FROM Paises P;
END
GO

EXEC getPaises
GO

CREATE PROCEDURE setPrecio
@name varchar(50),
@precioNuevo float

AS
BEGIN
	DECLARE @ID INT, @IDi INT;
	SELECT TOP 1 @ID=L.ID
	FROM Licores L
	WHERE L.nombre=@name;

	SELECT TOP 1 @IDi=I.ID
	FROM Inventarios I
	WHERE I.licorID=@ID;

	UPDATE Inventarios
	SET precio = @precioNuevo
	WHERE Inventarios.ID=@IDi;
END
GO

EXEC setPrecio 'Cerveza', 12031.93
GO

CREATE PROCEDURE getPrecio
@name varchar(50)

AS
BEGIN
	SELECT TOP 1 L.nombre, L.fotografia, I.precio
	FROM Licores L, Inventarios I
	WHERE L.nombre=@name AND L.ID=I.licorID;
END
GO

EXEC getPrecio 'Cerveza'
GO

CREATE PROCEDURE getProductosSinFactura

AS
BEGIN
	SELECT L.nombre, L.years, TA.tipo, L.fotografia, S.ID AS Sucursal, I.cantidad, I.precio, P.pais
	FROM Licores L, Paises P, TiposAnejado TA, Sucursales S, Inventarios I
	WHERE P.ID=L.procedenciaID AND TA.ID=L.tipoAnejadoID AND L.ID=I.licorID AND I.sucursalID=S.ID;
END
GO

EXEC getProductosSinFactura
GO

CREATE PROCEDURE getProductos

AS
BEGIN
	SELECT L.nombre, L.years, TA.tipo, L.fotografia, S.ID AS Sucursal, I.cantidad, I.precio, F.fecha, P.pais
	FROM Licores L, Paises P, TiposAnejado TA, Sucursales S, Inventarios I, Detalles D, Facturas F
	WHERE P.ID=L.procedenciaID AND TA.ID=L.tipoAnejadoID AND L.ID=I.licorID AND I.sucursalID=S.ID
	AND D.licorID=L.ID AND F.ID=D.facturaID;

END
GO

EXEC getProductos
GO

CREATE PROCEDURE getUsuarios
@tipo int

AS
BEGIN
	SELECT U.username, U.password FROM Usuarios U WHERE U.tipoUsuarioID=@tipo;
END
GO

EXEC getUsuarios 2
GO

CREATE PROCEDURE getUsers
AS
BEGIN
	SELECT * FROM Usuarios;
END
GO

EXEC getUsers
GO

CREATE PROCEDURE getPassword
	@username varchar(50)
AS
BEGIN
	DECLARE @password varchar(50)
	SET @password = (SELECT Usuarios.password FROM Usuarios)
	RETURN @password
END
GO

EXEC getPassword 'Morshu'
GO
