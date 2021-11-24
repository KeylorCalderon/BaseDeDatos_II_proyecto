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

