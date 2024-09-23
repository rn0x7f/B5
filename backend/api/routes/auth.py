from fastapi import APIRouter, Depends, HTTPException
from models.user import users
from schemas.user import User
from sqlalchemy.orm import Session
from typing import List
from sqlalchemy.exc import IntegrityError
from config.db import get_db, SECRET_KEY
import jwt
from datetime import datetime, timedelta, timezone
import bcrypt

auth = APIRouter()

# Función para hashear contraseñas
def hash_password(password: str) -> str:
    return bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

# Función para verificar contraseñas
def verify_password(plain_password: str, hashed_password: str) -> bool:
    return bcrypt.checkpw(plain_password.encode('utf-8'), hashed_password.encode('utf-8'))

# Función para crear un token JWT
def create_token(user_id: str) -> str:
    # Usa timezone.utc para obtener la fecha actual en UTC
    expiration = datetime.now(timezone.utc) + timedelta(hours=1)  # Asegúrate de usar timezone
    token = jwt.encode({"user_id": user_id, "exp": expiration.timestamp()}, SECRET_KEY, algorithm="HS256")
    return token


@auth.post("/signin")
async def signin(email: str, password: str, db: Session = Depends(get_db)):
    # Buscar el usuario por correo electrónico
    user = db.query(users).filter(users.c.correo_electronico == email).first()

    if not user:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    # Verificar la contraseña
    if not bcrypt.checkpw(password.encode('utf-8'), user.contrasena.encode('utf-8')):
        raise HTTPException(status_code=401, detail="Inicio de sesión no válido.")

    # Generar un token JWT usando el correo electrónico
    token = create_token(user.correo_electronico)  # Usa el correo electrónico
    return {"token": token}


@auth.post("/signup", response_model=User)
async def signup(user_data: User, db: Session = Depends(get_db)):
    new_user = users.insert().values(
        correo_electronico=user_data.correo_electronico,
        nombre=user_data.nombre,
        apellido=user_data.apellido,
        telefono=user_data.telefono,
        contrasena=hash_password(user_data.contrasena)
    )
    
    try:
        db.execute(new_user)
        db.commit()
    except IntegrityError:
        db.rollback()
        raise HTTPException(status_code=400, detail="El correo electrónico ya está registrado.")
    
    return user_data


@auth.post("/signout")
async def signout():
    return {"message": "Sign out"}