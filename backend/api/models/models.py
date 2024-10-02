from sqlalchemy import Column, Integer, String, Float, DateTime, ForeignKey
from sqlalchemy.orm import relationship
from config.db import Base, engine


# ______________________________________________________________________________________________________________________


# Modelo de la tabla Usuario
class Usuario(Base):
    __tablename__ = 'usuario'
    
    correo_electronico = Column(String(255), primary_key=True)
    nombre = Column(String(30), nullable=False)
    apellido = Column(String(30))
    telefono = Column(String(30))
    contrasena = Column(String(255), nullable=False)

    # Relación con la dirección de envío
    direcciones_envio = relationship("DireccionEnvioCliente", back_populates="usuario")

    # Relación con la tabla Compra
    compras = relationship("Compra", back_populates="usuario")


# ______________________________________________________________________________________________________________________


# Modelo de la tabla direccion_envio_cliente
class DireccionEnvioCliente(Base):
    __tablename__ = 'direccion_envio_cliente'
    
    correo_electronico = Column(String(255), ForeignKey('usuario.correo_electronico'), primary_key=True)
    direccion_envio = Column(String(255), primary_key=True)
    
    # Relación con el usuario
    usuario = relationship("Usuario", back_populates="direcciones_envio")


# ______________________________________________________________________________________________________________________


# Modelo de la tabla Catalogo
class Catalogo(Base):
    __tablename__ = 'catalogo'
    
    id_catalogo = Column(Integer, primary_key=True, autoincrement=True)
    nombre = Column(String(255), nullable=False)
    descripcion = Column(String(255))
    
    # Relación con los productos
    productos = relationship("Producto", back_populates="catalogo")


# ______________________________________________________________________________________________________________________


# Modelo de la tabla Producto
class Producto(Base):
    __tablename__ = 'producto'
    
    id_producto = Column(Integer, primary_key=True, autoincrement=True)
    id_catalogo = Column(Integer, ForeignKey('catalogo.id_catalogo'))
    nombre = Column(String(255), nullable=False)
    descripcion = Column(String(255))
    precio = Column(Float)
    categoria = Column(String(255))
    cantidad = Column(Integer)
    imagen = Column(String(255))
    
    # Relación con el catálogo
    catalogo = relationship("Catalogo", back_populates="productos")

    # Relación con la tabla Compra
    compras = relationship("Compra", back_populates="producto")

    # Relación con la tabla Administra
    administra = relationship("Administra", back_populates="producto")


# ______________________________________________________________________________________________________________________


# Modelo de la tabla Compra
class Compra(Base):
    __tablename__ = 'compra'
    
    correo_electronico = Column(String(255), ForeignKey('usuario.correo_electronico'), primary_key=True)
    id_producto = Column(Integer, ForeignKey('producto.id_producto'), primary_key=True)
    fecha_compra = Column(DateTime, primary_key=True)
    monto = Column(Float)
    numero_transaccion = Column(String(255), primary_key=True)
    cantidad = Column(Integer)
    
    # Relación con la tabla usuario
    usuario = relationship("Usuario", back_populates="compras")

    # Relación con la tabla producto
    producto = relationship("Producto", back_populates="compras")


# ______________________________________________________________________________________________________________________


# Modelo UsuarioAdmin
class UsuarioAdmin(Base):
    __tablename__ = 'usuario_admin'
    
    id_admin = Column(Integer, primary_key=True, autoincrement=True)
    nombre = Column(String(30), nullable=False)
    apellido = Column(String(30))
    contrasena = Column(String(255), nullable=False)
    
    # Relación con la tabla administra
    administra = relationship("Administra", back_populates="admin")


# ______________________________________________________________________________________________________________________


# Modelo Administra
class Administra(Base):
    __tablename__ = 'administra'
    
    id_admin = Column(Integer, ForeignKey('usuario_admin.id_admin'), primary_key=True)
    id_producto = Column(Integer, ForeignKey('producto.id_producto'), primary_key=True)
    
    # Relación la tabla administrador
    admin = relationship("UsuarioAdmin", back_populates="administra")

    # Relación la tabla producto
    producto = relationship("Producto", back_populates="administra")


# ______________________________________________________________________________________________________________________


# Crear todas las tablas en la base de datos
Base.metadata.create_all(engine)
