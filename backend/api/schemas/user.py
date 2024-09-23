from pydantic import BaseModel

class User(BaseModel):
    correo_electronico: str
    nombre: str
    apellido: str
    telefono: str
    contrasena: str
    
    class Config:
        orm_mode = True