CREATE PROCEDURE CRUD_Licores
	@opcion int,
	@licorID int,
	@paisID int,
	@years int,
	@tipoAnejadoID int,
	@fotografia image,
	@nombre varchar(50)
AS
BEGIN
	IF @opcion = 1 --create
	BEGIN
		INSERT INTO Licores
		VALUES(@paisID,@years,@tipoAnejadoID,@fotografia,@nombre)
	END

	ELSE IF @opcion = 2 --update
	BEGIN
		UPDATE Licores
		SET procedenciaID = ISNULL(@paisID,procedenciaID),
		years = ISNULL(@years,years),
		tipoAnejadoID = ISNULL(@tipoAnejadoID,tipoAnejadoID),
		fotografia = ISNULL(@fotografia,fotografia),
		nombre = ISNULL(@nombre,nombre)
	END
END
GO