from pydantic import BaseModel, EmailStr

class User(BaseModel):
    correo_electronico: EmailStr
    nombre: str
    apellido: str
    telefono: str
    contrasena: str
    
    class Config:
        from_attributes = True