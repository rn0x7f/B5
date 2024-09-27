from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from utils import usuarioCrud
from schemas.schemas import Usuario, UsuarioCreate
from config.db import SessionLocal

usuario = APIRouter()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@usuario.post("/", response_model=Usuario)
def create_user(usuario: UsuarioCreate, db: Session = Depends(get_db)):
    return usuarioCrud.create_usuario(db=db, usuario=usuario)

@usuario.get("/", response_model=list[Usuario])
def get_users(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    users = usuarioCrud.get_usuarios(db, skip=skip, limit=limit)
    return users

@usuario.get("/{email}", response_model=Usuario)
def get_user(email: str, db: Session = Depends(get_db)):
    user = usuarioCrud.get_usuario_by_email(db, email)
    if user is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    return user

@usuario.put("/{email}", response_model=Usuario)
def update_user(email: str, usuario: UsuarioCreate, db: Session = Depends(get_db)):
    return usuarioCrud.update_usuario(db, email, usuario)

@usuario.delete("/{email}")
def delete_user(email: str, db: Session = Depends(get_db)):
    return usuarioCrud.delete_usuario(db, email)