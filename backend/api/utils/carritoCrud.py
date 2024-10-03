from sqlalchemy.orm import Session
from schemas import schemas
from models import models
from fastapi import HTTPException

def add_to_cart(db: Session, carrito: schemas.CarritoCreate):
    # Primero verifica si ya existe el producto en el carrito
    existing_cart_item = db.query(models.Carrito).filter(models.Carrito.usuario_correo_electronico == carrito.usuario_correo_electronico, models.Carrito.producto_id == carrito.producto_id).first()
    if existing_cart_item:
        existing_cart_item.cantidad += carrito.cantidad
        db.commit()
        db.refresh(existing_cart_item)
        return existing_cart_item
    # Si no existe, crea un nuevo item
    db_carrito = models.Carrito(
        usuario_correo_electronico=carrito.usuario_correo_electronico,
        producto_id=carrito.producto_id,
        cantidad=carrito.cantidad
    )
    db.add(db_carrito)
    db.commit()
    db.refresh(db_carrito)
    return db_carrito

def get_cart(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Carrito).offset(skip).limit(limit).all()

def get_cart_by_user(db: Session, email: str):
    user = db.query(models.Usuario).filter(models.Usuario.correo_electronico == email).first()
    if user is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    return db.query(models.Carrito).filter(models.Carrito.usuario_correo_electronico == user.correo_electronico).all()