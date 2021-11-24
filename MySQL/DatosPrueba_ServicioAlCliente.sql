INSERT INTO TipoComentario(tipo)
VALUES ('Comentario positivo'),
  	   ('Comentario negativo'),
	   ('Queja');
       
INSERT INTO Agentes(nombre, apellidos, telefono, correo)
VALUES ('Keylor', 'Calderón Vega', 72125875, 'koiscal71@hotmail.com'),
  	   ('Sisi', 'Brenes Brenes', 49239423, 'sisi920@gmail.com'),
	   ('Juan', 'Perez Solano', 291822111, 'juanPS@gmail.com');
       
       
INSERT INTO Comentarios(comentario, tipoComentarioID, agenteID, empleadoID)
VALUES ('Muy buen servicio, entrega puntual y sin daños', 1, 1, 1),
  	   ('El envío se tardó más de lo esperado, y tenía golpes', 2, 2, 2),
	   ('6 de las 15 botellas venían rotas, y encima se tardaron 3 días más', 3, 3, 3);
       
SELECT * FROM TipoComentario;
SELECT * FROM Agentes;
SELECT * FROM Comentarios;