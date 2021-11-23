CREATE PROCEDURE productosMasVendidos
AS
BEGIN
	CREATE TABLE #ventas (ID int IDENTITY(1,1), licorID int,ventas int,sucursalID int)
	INSERT INTO #ventas
	SELECT licorID,count(licorID),sucursalID FROM Detalles INNER JOIN Facturas ON Facturas.ID = Detalles.facturaID GROUP BY Facturas.sucursalID, Detalles.licorID

	SELECT DISTINCT sucursalID as Sucursal, Licores.nombre FROM #ventas INNER JOIN Licores on #ventas.licorID = Licores.ID ORDER BY #ventas.ventas DESC

	DROP TABLE #ventas

END
GO
