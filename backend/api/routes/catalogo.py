from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from utils import catalogoCrud
from schemas.schemas import Catalogo, CatalogoCreate
from config.db import SessionLocal

catalogo = APIRouter()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@catalogo.post("/", response_model=Catalogo)
def create_catalog(catalogo: CatalogoCreate, db: Session = Depends(get_db)):
    return catalogoCrud.create_catalogo(db=db, catalogo=catalogo)

@catalogo.get("/", response_model=list[Catalogo])
def get_catalogs(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    catalogs = catalogoCrud.get_catalogos(db, skip=skip, limit=limit)
    return catalogs

@catalogo.get("/{catalogo_id}", response_model=Catalogo)
def get_catalog(catalogo_id: int, db: Session = Depends(get_db)):
    catalog = catalogoCrud.get_catalogo_by_id(db, catalogo_id)
    if catalog is None:
        raise HTTPException(status_code=404, detail="Catalogo no encontrado")
    return catalog

@catalogo.put("/{catalogo_id}", response_model=Catalogo)
def update_catalog(catalogo_id: int, catalogo: CatalogoCreate, db: Session = Depends(get_db)):
    return catalogoCrud.update_catalogo(db, catalogo_id, catalogo)

@catalogo.delete("/{catalogo_id}")
def delete_catalog(catalogo_id: int, db: Session = Depends(get_db)):
    return catalogoCrud.delete_catalogo(db, catalogo_id)