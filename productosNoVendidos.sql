CREATE PROCEDURE productosNoVendidos
AS
BEGIN
	CREATE TABLE #ventas (ID int IDENTITY(1,1), licorID int,sucursalID int)
	INSERT INTO #ventas
	SELECT DISTINCT licorID,sucursalID FROM Detalles INNER JOIN Facturas ON Facturas.ID = Detalles.facturaID

	SELECT Licores.nombre as Licor FROM  Licores 
	WHERE Licores.ID not in (SELECT licorID FROM #ventas)

	DROP TABLE #ventas

END
GO