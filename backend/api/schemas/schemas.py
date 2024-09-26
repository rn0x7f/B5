from pydantic import BaseModel, EmailStr


# ____________________________________________________________________


# Datos base de un usuario
class UsuarioBase(BaseModel):
    correo_electronico: EmailStr
    nombre: str
    apellido: str
    telefono: str

# Datos que se reciben del cliente para crear un usuario
class UsuarioCreate(UsuarioBase):
    contrasena: str

# Datos que se muestran al cliente
class Usuario(UsuarioBase):
    direcciones_envio: list["DireccionEnvioCliente"] = []
    compras: list["Compra"] = []

    class Config:
        from_attributes = True


# ____________________________________________________________________


# Datos base de una dirección de envío
class DireccionEnvioClienteBase(BaseModel):
    correo_electronico: EmailStr
    direccion_envio: str

# Datos que se reciben del cliente para crear una dirección de envío
class DireccionEnvioClienteCreate(DireccionEnvioClienteBase):
    pass

# Datos que se muestran al cliente
class DireccionEnvioCliente(DireccionEnvioClienteBase):
    usuario: Usuario

    class Config:
        from_attributes = True


# ____________________________________________________________________


# Datos base de un catálogo
class CatalogoBase(BaseModel):
    nombre: str
    descripcion: str | None = None

# Datos que se reciben del cliente para crear un catálogo
class CatalogoCreate(CatalogoBase):
    pass

# Datos que se muestran al cliente
class Catalogo(CatalogoBase):
    id_catalogo: int
    productos: list["Producto"] = []

    class Config:
        from_attributes = True


# ____________________________________________________________________


# Datos base de un producto
class ProductoBase(BaseModel):
    nombre: str
    descripcion: str | None = None
    precio: float
    categoria: str
    cantidad: int
    imagen: str

# Datos que se reciben del cliente para crear un producto
class ProductoCreate(ProductoBase):
    id_catalogo: int

# Datos que se muestran al cliente
class Producto(ProductoBase):
    id_producto: int
    catalogo: Catalogo

    class Config:
        from_attributes = True


# ____________________________________________________________________


# Datos base de una compra
class CompraBase(BaseModel):
    id_producto: int
    monto: float
    cantidad: int
    correo_electronico: EmailStr
    numero_transaccion: str

# Datos que se reciben del cliente para crear una compra
class CompraCreate(CompraBase):
    pass

# Datos que se muestran al cliente
class Compra(CompraBase):
    producto: Producto
    usuario: Usuario
    fecha_compra: str

    class Config:
        from_attributes = True


# ____________________________________________________________________


# Datos base de un administrador
class UsuarioAdminBase(BaseModel):
    nombre: str
    apellido: str

class UsuarioAdminCreate(UsuarioAdminBase):
    contrasena: str

class UsuarioAdmin(UsuarioAdminBase):
    id_admin: int

    class Config:
        from_attributes = True


# ____________________________________________________________________


# Datos base de una relación administra
class AdministraBase(BaseModel):
    id_producto: int
    id_admin: int

class AdministraCreate(AdministraBase):
    pass

class Administra(AdministraBase):
    admin: UsuarioAdmin
    producto: Producto

    class Config:
        from_attributes = True