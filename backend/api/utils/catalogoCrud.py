from sqlalchemy.orm import Session
from fastapi import HTTPException
from models import models
from schemas import schemas

def get_catalogo_by_id(db: Session, catalogo_id: int):
    return db.query(models.Catalogo).filter(models.Catalogo.id_catalogo == catalogo_id).first()

def get_catalogos(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Catalogo).offset(skip).limit(limit).all()

def create_catalogo(db: Session, catalogo: schemas.CatalogoCreate):
    db_catalogo = models.Catalogo(
        nombre=catalogo.nombre,
        descripcion=catalogo.descripcion
    )
    db.add(db_catalogo)
    db.commit()
    db.refresh(db_catalogo)
    return db_catalogo

def update_catalogo(db: Session, catalogo_id: int, catalogo_update: schemas.CatalogoCreate):
    db_catalogo = db.query(models.Catalogo).filter(models.Catalogo.id_catalogo == catalogo_id).first()

    if db_catalogo is None:
        raise HTTPException(status_code=404, detail="Catalogo no encontrado")

    if catalogo_update.nombre:
        db_catalogo.nombre = catalogo_update.nombre

    if catalogo_update.descripcion:
        db_catalogo.descripcion = catalogo_update.descripcion

    db.commit()
    db.refresh(db_catalogo)

    return db_catalogo

def delete_catalogo(db: Session, catalogo_id: int):
    db_catalogo = db.query(models.Catalogo).filter(models.Catalogo.id_catalogo == catalogo_id).first()
    db.delete(db_catalogo)
    db.commit()
    return db_catalogo