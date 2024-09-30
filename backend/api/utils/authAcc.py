from utils import usuarioCrud
from schemas import schemas
from fastapi import HTTPException
from sqlalchemy.orm import Session
import bcrypt
from jose import jwt, JWTError
from config.db import SECRET_KEY
from datetime import datetime, timedelta, timezone

TOKEN_SECONDS_EXP = 1296000

def create_token(data: list):
    token_data = data.copy()
    token_data["exp"] = datetime.now(timezone.utc) + timedelta(seconds=TOKEN_SECONDS_EXP)
    token_jwt = jwt.encode(token_data, key=SECRET_KEY, algorithm='HS256')
    return token_jwt

def is_valid_token(db: Session, token: str):
    try:
        token_data = jwt.decode(token, key=SECRET_KEY, algorithms=['HS256'])
        email = token_data["email"]
        db_usuario = usuarioCrud.get_usuario_by_email(db, email)
        if db_usuario:
            return True
        return False
    except JWTError:
        return False

def verify_password(plain_password: str, hashed_password: str) -> bool:
    return bcrypt.checkpw(plain_password.encode('utf-8'), hashed_password.encode('utf-8'))

def signup(db: Session, usuario: schemas.UsuarioCreate):
    return usuarioCrud.create_usuario(db, usuario)

def signin(db: Session, email: str, password: str):
    # Buscar el usuario por correo electrónico
    db_usuario = usuarioCrud.get_usuario_by_email(db, email)
    
    if not db_usuario:
        raise HTTPException(status_code=400, detail="Correo o contraseña incorrecta")
    
    # Verificar que la contraseña ingresada sea correcta
    if not verify_password(password, db_usuario.contrasena):
        raise HTTPException(status_code=400, detail="Correo o contraseña incorrecta")
    
    token = create_token({"email": email})
    return {"token": token}