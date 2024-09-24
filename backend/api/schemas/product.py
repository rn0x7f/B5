from pydantic import BaseModel

class Product(BaseModel):
    id_catalogo: int
    id_producto: int
    nombre: str
    descripcion: str
    precio: float
    categoria: str
    cantidad: int
    
    class Config:
        from_attributes = True