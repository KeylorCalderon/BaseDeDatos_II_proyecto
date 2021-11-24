USE [base_CostaRica]
--USE [base_Nicaragua]
--USE [base_Panama]
GO

CREATE PROCEDURE productosMasVendidos
AS
BEGIN
	CREATE TABLE #ventas (ID int IDENTITY(1,1), licorID int,ventas int,sucursalID int)
	INSERT INTO #ventas
	SELECT licorID,count(licorID),sucursalID FROM Detalles, Facturas WHERE Facturas.ID = Detalles.facturaID GROUP BY Facturas.sucursalID, Detalles.licorID

	SELECT DISTINCT V.sucursalID as Sucursal, L.nombre, V.ventas AS Ventas FROM #ventas V, Licores L WHERE V.licorID = L.ID ORDER BY V.ventas DESC

	DROP TABLE #ventas

END
GO

CREATE PROCEDURE productosMenosVendidos
AS
BEGIN
	CREATE TABLE #ventas (ID int IDENTITY(1,1), licorID int,ventas int,sucursalID int)
	INSERT INTO #ventas
	SELECT licorID,count(licorID),sucursalID FROM Detalles, Facturas WHERE Facturas.ID = Detalles.facturaID GROUP BY Facturas.sucursalID, Detalles.licorID

	SELECT DISTINCT V.sucursalID as Sucursal, L.nombre, V.ventas AS Ventas FROM #ventas V, Licores L WHERE V.licorID = L.ID ORDER BY V.ventas ASC

	DROP TABLE #ventas

END
GO

CREATE PROCEDURE productosNoVendidos
AS
BEGIN
	CREATE TABLE #ventas (ID int IDENTITY(1,1), licorID int,ventas int,sucursalID int)
	INSERT INTO #ventas
	SELECT 
		licorID,count(licorID),sucursalID 
	FROM 
		Detalles, Facturas 
	WHERE 
		Facturas.ID = Detalles.facturaID 
	GROUP BY 
		Facturas.sucursalID, Detalles.licorID;

	SELECT DISTINCT 
		I.sucursalID as Sucursal, L.nombre, P.pais
	FROM 
		#ventas V, Licores L, Inventarios I, Paises P
	WHERE 
		V.licorID != L.ID AND L.ID=I.licorID AND P.ID=L.procedenciaID

	DROP TABLE #ventas

END
GO

CREATE PROCEDURE ventasXtipoPagoXSucursalXFechas
AS
BEGIN
	CREATE TABLE #ventas (ID int IDENTITY(1,1), licorID int,ventas int,sucursalID int, fecha date, tipoPago int)
	INSERT INTO #ventas
	SELECT
		licorID, count(licorID), sucursalID, Facturas.fecha,
		CASE Facturas.impuestosTarjeta
			WHEN 0 THEN 2
			ELSE 1
		END
		AS tipoPago
	FROM
		Detalles, Facturas
	WHERE
		Facturas.ID = Detalles.facturaID
	GROUP BY
		Facturas.sucursalID, Detalles.licorID, Facturas.fecha, Facturas.impuestosTarjeta;

	SELECT DISTINCT 
		V.sucursalID as Sucursal, L.nombre, V.ventas AS Ventas, V.fecha, V.tipoPago
	FROM
		#ventas V, Licores L
	WHERE
		V.licorID = L.ID
	ORDER BY
		V.ventas DESC

	DROP TABLE #ventas

END
GO

CREATE PROCEDURE verComentarios
AS
BEGIN
	SELECT * FROM OPENQUERY(MYSQL_BD, 'SELECT C.comentario, C.tipoComentarioID, C.agenteID, C.empleadoID FROM Comentarios C;');
END
GO

CREATE PROCEDURE comentarios
@inComentario varchar(50),
@inTipoComentario INT,
@inAgenteID INT,
@inEmpleadoID INT

AS
BEGIN
	BEGIN TRY
		INSERT INTO
			OPENQUERY(MYSQL_BD,'SELECT C.comentario, C.tipoComentarioID, C.agenteID, C.empleadoID FROM Comentarios C;')
			VALUES (@inComentario, @inTipoComentario, @inAgenteID, @inEmpleadoID);
	END TRY
	BEGIN CATCH
		SELECT 'ERROR EN LA OPERACIÓN: '+ERROR_MESSAGE();
	END CATCH
END
GO

-------------------------------------------------------------------------------------
EXEC productosMasVendidos
GO

EXEC productosMenosVendidos
GO

EXEC productosNoVendidos
GO

EXEC ventasXtipoPagoXSucursalXFechas
GO

EXEC verComentarios
GO

EXEC comentarios 'Mal servicio, no recomendado', 2, 2, 2
GO