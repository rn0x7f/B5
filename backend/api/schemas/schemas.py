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
    pass
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
    id_catalogo: int
    
# Datos que se reciben del cliente para crear un producto
class ProductoCreate(ProductoBase):
    pass

# Datos que se muestran al cliente
class Producto(ProductoBase):
    id_producto: int

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


# ____________________________________________________________________

class PaymentRequest(BaseModel):
    amount: int  # Monto en centavos (e.g. $10.00 sería 1000 centavos)
    currency: str
    description: str | None = None
    source: str  # Token de la tarjeta o método de pago


# ____________________________________________________________________


# Datos base de un carrito
class CarritoBase(BaseModel):
    usuario_correo_electronico: EmailStr
    producto_id: int
    cantidad: int

# Datos que se reciben del cliente
class CarritoCreate(CarritoBase):
    pass

# Datos que se muestran al cliente
class Carrito(CarritoBase):
    # Toma el nombre del producto y el precio del producto con su id
    producto: Producto

    class Config:
        from_attributes = True



class Login(BaseModel):
    email: str
    password: str