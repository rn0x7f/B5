from sqlalchemy.orm import Session
from schemas import schemas
from models import models
from fastapi import HTTPException

def add_to_cart(db: Session, carrito: schemas.CarritoCreate):
    existing_cart_item = db.query(models.Carrito).filter(
        models.Carrito.usuario_correo_electronico == carrito.usuario_correo_electronico,
        models.Carrito.producto_id == carrito.producto_id
    ).first()

    producto = db.query(models.Producto).filter(models.Producto.id_producto == carrito.producto_id).first()
    if not producto:
        raise HTTPException(status_code=404, detail="Producto no encontrado")

    if producto.cantidad < carrito.cantidad:
        raise HTTPException(status_code=400, detail="No hay suficiente stock")

    producto.cantidad -= carrito.cantidad
    db.commit()

    if existing_cart_item:
        existing_cart_item.cantidad += carrito.cantidad
        db.commit()
        db.refresh(existing_cart_item)
        return existing_cart_item

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

def delete_from_cart(db: Session, email: str, product_id: int):
    user = db.query(models.Usuario).filter(models.Usuario.correo_electronico == email).first()
    if user is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    
    item = db.query(models.Carrito).filter(
        models.Carrito.usuario_correo_electronico == user.correo_electronico,
        models.Carrito.producto_id == product_id
    ).first()
    
    if item is None:
        raise HTTPException(status_code=404, detail="Producto no encontrado en el carrito")
    
    producto = db.query(models.Producto).filter(models.Producto.id_producto == product_id).first()
    if producto is None:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    
    item.cantidad -= 1
    
    producto.cantidad += 1
    
    if item.cantidad <= 0:
        db.delete(item)
    
    db.commit()
    
    return item


def delete_cart(db: Session, email: str):
    user = db.query(models.Usuario).filter(models.Usuario.correo_electronico == email).first()
    if user is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    items = db.query(models.Carrito).filter(models.Carrito.usuario_correo_electronico == user.correo_electronico).all()
    if not items:
        raise HTTPException(status_code=404, detail="No hay productos en el carrito")

    for item in items:
        producto = db.query(models.Producto).filter(models.Producto.id_producto == item.producto_id).first()
        if producto:
            producto.cantidad += item.cantidad

    for item in items:
        db.delete(item)

    db.commit()

    return {"msg" : "Carrito eliminado"}