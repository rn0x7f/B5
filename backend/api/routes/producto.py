from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from utils import productoCrud, catalogoCrud
from schemas.schemas import Producto, ProductoCreate
from config.db import SessionLocal

producto = APIRouter()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@producto.post("/", response_model=Producto)
def create_product(producto: ProductoCreate, db: Session = Depends(get_db)):
    # Si no hay catalogo con ese id, lanzar error
    if catalogoCrud.get_catalogo_by_id(db, producto.id_catalogo) is None:
        raise HTTPException(status_code=404, detail="Catalogo no encontrado")
    return productoCrud.create_producto(db=db, producto=producto)

@producto.get("/", response_model=list[Producto])
def get_products(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    products = productoCrud.get_productos(db, skip=skip, limit=limit)
    return products

@producto.get("/{producto_id}", response_model=Producto)
def get_product(producto_id: int, db: Session = Depends(get_db)):
    product = productoCrud.get_producto_by_id(db, producto_id)
    if product is None:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    return product

@producto.put("/{producto_id}", response_model=Producto)
def update_product(producto_id: int, producto: ProductoCreate, db: Session = Depends(get_db)):
    return productoCrud.update_producto(db, producto_id, producto)

@producto.delete("/{producto_id}")
def delete_product(producto_id: int, db: Session = Depends(get_db)):
    return productoCrud.delete_producto(db, producto_id)