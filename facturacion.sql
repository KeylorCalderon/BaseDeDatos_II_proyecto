CREATE PROCEDURE facturacion
@sucursalID int,
@clienteID int,
@empleadoID int,
@envio bit,
@carritoJson varchar(max),
@montoParaDescuento float,
@pagoConTarjeta bit
AS
BEGIN
	--revisar cliente
	IF ((SELECT DATEDIFF(year,getdate(),Clientes.fechaNacimiento) FROM Clientes WHERE Clientes.ID = @clienteID) < 18)
	BEGIN
		RETURN 'Cliente menor de edad'
	END

	--crear factura
	INSERT INTO Facturas (fecha,clienteID,empleadoID,sucursalID,envio,total,impuestosTarjeta)
	VALUES(GETDATE(),@clienteID,@empleadoID,@sucursalID,@envio,0,0)

	DECLARE @facturaID int
	SET @facturaID = (SELECT SCOPE_IDENTITY() FROM Facturas)

	--crear detalles
	CREATE TABLE #compras (ID int IDENTITY(1,1),licorID varchar(50),cantidad varchar(10))
	INSERT INTO #compras (licorID,cantidad)
	SELECT 
		CAST(j.licorID AS INT),
		CAST(j.cantidad AS INT)
	FROM
		OPENJSON(@carritoJson) 
		WITH(
			licorID int '$.licorID',
			cantidad int '$.cantidad'
		) j

	INSERT INTO Detalles (facturaID,licorID,cantidad,subtotal)
	SELECT @facturaID, #compras.licorID, #compras.cantidad, (SELECT Inventarios.precio*#compras.cantidad FROM Inventarios 
																INNER JOIN #compras on Inventarios.licorID = #compras.licorID
																WHERE Inventarios.sucursalID = @sucursalID)
	FROM #compras							

	--generar el total
	UPDATE Facturas
	SET total = (SELECT SUM(subtotal) FROM Detalles WHERE facturaID = @facturaID)
	WHERE ID = @facturaID

	--aplicar descuento y revisar si hay envio
	IF((SELECT SUM(total) FROM Facturas WHERE MONTH(Facturas.fecha) = MONTH(GETDATE()) AND Facturas.clienteID = @clienteID ) >= @montoParaDescuento)
	BEGIN
		UPDATE Facturas
		SET total = total - (total*0.1)
		WHERE ID = @facturaID
	END

	IF @envio = 1
	BEGIN
		--revisar si no es vip
		IF((SELECT count(ID) FROM Facturas WHERE  MONTH(Facturas.fecha) = MONTH(GETDATE()) AND clienteID = @clienteID) <= 5)
		BEGIN
			UPDATE Facturas
			SET total = total + 10 --10 es el monto arbritario de envio porque no se especifica
			WHERE ID = @facturaID
		END
	END

	--retencion de tarjeta
	IF @pagoConTarjeta = 1
	BEGIN
		UPDATE Facturas
		SET impuestosTarjeta = total*0.1,
		tipoPago = (SELECT ID FROM TiposDePago WHERE tipo = 'tarjeta')
		WHERE ID = @facturaID
	END
END
GO