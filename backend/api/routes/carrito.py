from fastapi import APIRouter, HTTPException, Depends
from schemas.schemas import Carrito, CarritoCreate
from sqlalchemy.orm import Session
from config.db import SessionLocal
from utils import carritoCrud

carrito = APIRouter()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@carrito.post("/", response_model=Carrito)
def add_to_cart(carrito: CarritoCreate, db: Session = Depends(get_db)):
    return carritoCrud.add_to_cart(db=db, carrito=carrito)

@carrito.get("/", response_model=list[Carrito])
def get_cart(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    cart = carritoCrud.get_cart(db, skip=skip, limit=limit)
    return cart

@carrito.get("/{email}", response_model=list[Carrito])
def get_cart_by_user(email: str, db: Session = Depends(get_db)):
    cart = carritoCrud.get_cart_by_user(db, email)
    return cart