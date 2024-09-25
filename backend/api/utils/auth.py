from fastapi import Depends, HTTPException
from sqlalchemy.orm import Session
from models.models import users
from config.db import get_db, SECRET_KEY
import jwt
from fastapi.security import OAuth2PasswordBearer

# Definición del esquema de seguridad para el token
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="auth/signin")

def get_current_user(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    credentials_exception = HTTPException(
        status_code=401,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        user_id = payload.get("user_id")
        if user_id is None:
            raise credentials_exception
        user = db.query(users).filter(users.c.correo_electronico == user_id).first()
        if user is None:
            raise credentials_exception
    except jwt.PyJWTError:
        raise credentials_exception
    return user
