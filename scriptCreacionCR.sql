CREATE DATABASE base_CostaRica
GO
USE [base_CostaRica]
GO
/****** Object:  Table [dbo].[Amonestaciones]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Amonestaciones](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[fecha] [date] NOT NULL,
	[empleadoID] [int] NOT NULL,
	[descripcion] [varchar](max) NOT NULL,
 CONSTRAINT [PK_Amonestaciones] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Clientes](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[apellidos] [varchar](50) NOT NULL,
	[telefono] [int] NOT NULL,
	[correo] [varchar](50) NOT NULL,
	[usuarioID] [int] NOT NULL,
	[fechaNacimiento] [date] NOT NULL,
 CONSTRAINT [PK_Clientes] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CombinacionesDeComida]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CombinacionesDeComida](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[licorID] [int] NOT NULL,
	[comidaID] [int] NOT NULL,
 CONSTRAINT [PK_CombinacionesDeComida] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Comidas]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comidas](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[comida] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Comidas] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Detalles]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Detalles](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[facturaID] [int] NOT NULL,
	[licorID] [int] NOT NULL,
	[cantidad] [int] NOT NULL,
	[subtotal] [float] NOT NULL,
 CONSTRAINT [PK_Detalles] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Empleados]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Empleados](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
	[apellidos] [varchar](50) NOT NULL,
	[telefono] [int] NOT NULL,
	[correo] [varchar](50) NOT NULL,
	[sucursalID] [int] NOT NULL,
	[fotografia] varbinary(max) NOT NULL,
	[usuarioID] [int] NOT NULL,
 CONSTRAINT [PK_Empleados] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Facturas]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Facturas](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[fecha] [date] NOT NULL,
	[clienteID] [int] NOT NULL,
	[empleadoID] [int] NOT NULL,
	[sucursalID] [int] NOT NULL,
	[envio] [bit] NOT NULL,
	[total] [float] NOT NULL,
	[impuestosTarjeta] [float]
 CONSTRAINT [PK_Facturas] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Inventarios]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Inventarios](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[licorID] [int] NOT NULL,
	[sucursalID] [int] NOT NULL,
	[cantidad] [int] NOT NULL,
	[precio] [float] NOT NULL,
 CONSTRAINT [PK_Inventarios] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Licores]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Licores](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[procedenciaID] [int] NOT NULL,
	[years] [int] NOT NULL,
	[tipoAnejadoID] [int] NOT NULL,
	[fotografia] varbinary(max) NOT NULL,
	[nombre] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Licores] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Paises]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Paises](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[pais] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Paises] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sucursales]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sucursales](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[paisID] [int] NOT NULL,
	[ubicacion] [geography] NOT NULL,
 CONSTRAINT [PK_Sucursales] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TiposAnejado]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TiposAnejado](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[tipo] [varchar](50) NOT NULL,
 CONSTRAINT [PK_TiposAnejado] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TiposDeUsuario]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TiposDeUsuario](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[tipo] [varchar](50) NOT NULL,
 CONSTRAINT [PK_TiposDeUsuario] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 19/11/2021 20:36:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuarios](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[tipoUsuarioID] [int] NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Amonestaciones]  WITH CHECK ADD  CONSTRAINT [FK_Amonestaciones_Empleados] FOREIGN KEY([empleadoID])
REFERENCES [dbo].[Empleados] ([ID])
GO
ALTER TABLE [dbo].[Amonestaciones] CHECK CONSTRAINT [FK_Amonestaciones_Empleados]
GO
ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_Usuarios] FOREIGN KEY([usuarioID])
REFERENCES [dbo].[Usuarios] ([ID])
GO
ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_Clientes_Usuarios]
GO
ALTER TABLE [dbo].[CombinacionesDeComida]  WITH CHECK ADD  CONSTRAINT [FK_CombinacionesDeComida_Comidas] FOREIGN KEY([comidaID])
REFERENCES [dbo].[Comidas] ([ID])
GO
ALTER TABLE [dbo].[CombinacionesDeComida] CHECK CONSTRAINT [FK_CombinacionesDeComida_Comidas]
GO
ALTER TABLE [dbo].[CombinacionesDeComida]  WITH CHECK ADD  CONSTRAINT [FK_CombinacionesDeComida_Licores] FOREIGN KEY([licorID])
REFERENCES [dbo].[Licores] ([ID])
GO
ALTER TABLE [dbo].[CombinacionesDeComida] CHECK CONSTRAINT [FK_CombinacionesDeComida_Licores]
GO
ALTER TABLE [dbo].[Detalles]  WITH CHECK ADD  CONSTRAINT [FK_Detalles_Facturas] FOREIGN KEY([facturaID])
REFERENCES [dbo].[Facturas] ([ID])
GO
ALTER TABLE [dbo].[Detalles] CHECK CONSTRAINT [FK_Detalles_Facturas]
GO
ALTER TABLE [dbo].[Detalles]  WITH CHECK ADD  CONSTRAINT [FK_Detalles_Licores] FOREIGN KEY([licorID])
REFERENCES [dbo].[Licores] ([ID])
GO
ALTER TABLE [dbo].[Detalles] CHECK CONSTRAINT [FK_Detalles_Licores]
GO
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_Empleados_Sucursales] FOREIGN KEY([sucursalID])
REFERENCES [dbo].[Sucursales] ([ID])
GO
ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_Empleados_Sucursales]
GO
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_Empleados_Usuarios] FOREIGN KEY([usuarioID])
REFERENCES [dbo].[Usuarios] ([ID])
GO
ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_Empleados_Usuarios]
GO
ALTER TABLE [dbo].[Facturas]  WITH CHECK ADD  CONSTRAINT [FK_Facturas_Clientes] FOREIGN KEY([clienteID])
REFERENCES [dbo].[Clientes] ([ID])
GO
ALTER TABLE [dbo].[Facturas] CHECK CONSTRAINT [FK_Facturas_Clientes]
GO
ALTER TABLE [dbo].[Facturas]  WITH CHECK ADD  CONSTRAINT [FK_Facturas_Empleados] FOREIGN KEY([empleadoID])
REFERENCES [dbo].[Empleados] ([ID])
GO
ALTER TABLE [dbo].[Facturas] CHECK CONSTRAINT [FK_Facturas_Empleados]
GO
ALTER TABLE [dbo].[Facturas]  WITH CHECK ADD  CONSTRAINT [FK_Facturas_Sucursales] FOREIGN KEY([sucursalID])
REFERENCES [dbo].[Sucursales] ([ID])
GO
ALTER TABLE [dbo].[Facturas] CHECK CONSTRAINT [FK_Facturas_Sucursales]
GO
ALTER TABLE [dbo].[Inventarios]  WITH CHECK ADD  CONSTRAINT [FK_Inventarios_Licores] FOREIGN KEY([licorID])
REFERENCES [dbo].[Licores] ([ID])
GO
ALTER TABLE [dbo].[Inventarios] CHECK CONSTRAINT [FK_Inventarios_Licores]
GO
ALTER TABLE [dbo].[Inventarios]  WITH CHECK ADD  CONSTRAINT [FK_Inventarios_Sucursales] FOREIGN KEY([sucursalID])
REFERENCES [dbo].[Sucursales] ([ID])
GO
ALTER TABLE [dbo].[Inventarios] CHECK CONSTRAINT [FK_Inventarios_Sucursales]
GO
ALTER TABLE [dbo].[Licores]  WITH CHECK ADD  CONSTRAINT [FK_Licores_Paises] FOREIGN KEY([procedenciaID])
REFERENCES [dbo].[Paises] ([ID])
GO
ALTER TABLE [dbo].[Licores] CHECK CONSTRAINT [FK_Licores_Paises]
GO
ALTER TABLE [dbo].[Licores]  WITH CHECK ADD  CONSTRAINT [FK_Licores_TiposAnejado] FOREIGN KEY([tipoAnejadoID])
REFERENCES [dbo].[TiposAnejado] ([ID])
GO
ALTER TABLE [dbo].[Licores] CHECK CONSTRAINT [FK_Licores_TiposAnejado]
GO
ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_Sucursales_Paises] FOREIGN KEY([paisID])
REFERENCES [dbo].[Paises] ([ID])
GO
ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_Sucursales_Paises]
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_Usuarios_TiposDeUsuario] FOREIGN KEY([tipoUsuarioID])
REFERENCES [dbo].[TiposDeUsuario] ([ID])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_Usuarios_TiposDeUsuario]
GO

CREATE TABLE TipoMetodoPago(Id INT IDENTITY(1,1) PRIMARY KEY,
					    Nombre VARCHAR(50))
GO

CREATE TABLE MetodoPago(Id INT IDENTITY(1,1) PRIMARY KEY,
					    TipoId INT,
					    IdCliente INT,
						FOREIGN KEY (IdCliente) REFERENCES Clientes (ID),
						FOREIGN KEY (TipoId) REFERENCES TipoMetodoPago (Id))
GO

CREATE TABLE FacturaTemporal(ID INT IDENTITY(1,1) PRIMARY KEY,
					    fecha DATE,
					    clienteID INT,
						sucursalID INT, 
						envio BIT,
						licorID INT,
						cantidad INT,
						subtotal INT,
						metodoDePago INT,
						pendiente BIT,
						FOREIGN KEY (clienteID) REFERENCES Clientes (ID),
						FOREIGN KEY (sucursalID) REFERENCES Sucursales (ID),
						FOREIGN KEY (licorID) REFERENCES Licores (ID))
GO