from sqlalchemy.orm import Session
from fastapi import HTTPException
from models import models
from schemas import schemas

def get_producto_by_id(db: Session, producto_id: int):
    return db.query(models.Producto).filter(models.Producto.id_producto == producto_id).first()

def get_productos(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Producto).offset(skip).limit(limit).all() 

def create_producto(db: Session, producto: schemas.ProductoCreate):
    db_producto = models.Producto(
        nombre=producto.nombre,
        descripcion=producto.descripcion,
        precio=producto.precio,
        categoria=producto.categoria,
        cantidad=producto.cantidad,
        imagen=producto.imagen,
        id_catalogo=producto.id_catalogo
    )
    db.add(db_producto)
    db.commit()
    db.refresh(db_producto)
    return db_producto

def update_producto(db: Session, producto_id: int, producto_update: schemas.ProductoCreate):
    db_producto = db.query(models.Producto).filter(models.Producto.id_producto == producto_id).first()

    if db_producto is None:
        raise HTTPException(status_code=404, detail="Producto no encontrado")

    if producto_update.nombre:
        db_producto.nombre = producto_update.nombre

    if producto_update.descripcion:
        db_producto.descripcion = producto_update.descripcion

    if producto_update.precio:
        db_producto.precio = producto_update.precio

    if producto_update.categoria:
        db_producto.categoria = producto_update.categoria
    
    if producto_update.cantidad:
        db_producto.cantidad = producto_update.cantidad
    
    if producto_update.imagen:
        db_producto.imagen = producto_update.imagen

    if producto_update.id_catalogo:
        db_producto.id_catalogo = producto_update.id_catalogo

    db.commit()
    db.refresh(db_producto)

    return db_producto

def delete_producto(db: Session, producto_id: int):
    db_producto = db.query(models.Producto).filter(models.Producto.id_producto == producto_id).first()

    if db_producto is None:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    
    db.delete(db_producto)
    db.commit()
    
    return db_producto