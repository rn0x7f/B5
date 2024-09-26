from sqlalchemy.orm import Session
from fastapi import HTTPException
from models import models
from schemas import schemas
import bcrypt
import re

email_regex = r'^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$'

# Función para validar el correo electrónico
def is_valid_email(correo_electronico: str) -> bool:
    return re.match(email_regex, correo_electronico) is not None

# Función para generar el hash de la contraseña usando bcrypt
def hash_password(password: str) -> str:
    return bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

def get_usuario_by_email(db: Session, email: str):
    return db.query(models.Usuario).filter(models.Usuario.correo_electronico == email).first()

def get_usuarios(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Usuario).offset(skip).limit(limit).all()

def create_usuario(db: Session, usuario: schemas.UsuarioCreate):
    if not is_valid_email(usuario.correo_electronico):
        raise HTTPException(status_code=400, detail="Correo electrónico no válido")
    
    existing_user = get_usuario_by_email(db, usuario.correo_electronico)
    if existing_user:
        raise HTTPException(status_code=400, detail="Usuario con este correo ya existe")
    
    hashed_password = hash_password(usuario.contrasena)

    db_usuario = models.Usuario(
        correo_electronico=usuario.correo_electronico,
        nombre=usuario.nombre,
        apellido=usuario.apellido,
        telefono=usuario.telefono,
        contrasena=hashed_password)
    
    db.add(db_usuario)
    db.commit()
    db.refresh(db_usuario)
    return db_usuario

def update_usuario(db: Session, correo_electronico: str, usuario_update: schemas.UsuarioCreate):
    # Buscar el usuario por correo electrónico
    db_usuario = db.query(models.Usuario).filter(models.Usuario.correo_electronico == correo_electronico).first()

    if db_usuario is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    if usuario_update.nombre:
        db_usuario.nombre = usuario_update.nombre
    
    if usuario_update.apellido:
        db_usuario.apellido = usuario_update.apellido
    
    if usuario_update.telefono:
        db_usuario.telefono = usuario_update.telefono
    
    if usuario_update.contrasena:
        db_usuario.contrasena = hash_password(usuario_update.contrasena)

    db.commit()
    db.refresh(db_usuario)

    return db_usuario