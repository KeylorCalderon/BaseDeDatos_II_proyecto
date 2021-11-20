CREATE PROCEDURE getPassword
	@username varchar(50)
AS
BEGIN
	DECLARE @password varchar(50)
	SET @password = (SELECT Usuarios.password FROM Usuarios)
	RETURN @password
END
GO
