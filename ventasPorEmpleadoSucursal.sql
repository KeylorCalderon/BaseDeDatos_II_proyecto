CREATE PROCEDURE ventasPorEmpleadoSucursal
AS
BEGIN
	SELECT 'Empleado' = CASE
		WHEN GROUPING(Facturas.empleadoID) = 1 THEN 'Todos'
		ELSE ISNULL(Facturas.empleadoID, 'N/D')
		END,
		'Sucursal' = CASE
		WHEN GROUPING(Facturas.sucursalID) = 1 THEN 'Todas'
		ELSE ISNULL(Facturas.sucursalID,'N/D')
		END,
		SUM(Facturas.total) as Ventas
	FROM Facturas WHERE MONTH(Facturas.fecha) = MONTH(GETDATE())
	GROUP BY Facturas.empleadoID,Facturas.sucursalID WITH CUBE

END
GO