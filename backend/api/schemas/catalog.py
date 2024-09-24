from pydantic import BaseModel

class Catalog(BaseModel):
    id_catalogo: int
    nombre: str
    descripcion: str
    
    class Config:
        from_attributes = True