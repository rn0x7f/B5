from fastapi import APIRouter, Depends
from models.models import products
from schemas.product import Product
from schemas.user import User
from sqlalchemy.orm import Session
from typing import List
from config.db import get_db
from utils.auth import get_current_user

product = APIRouter()

# Obtener todos los productos
@product.get("/", response_model=List[Product])
async def get_products(current_user: User = Depends(get_current_user), db: Session = Depends(get_db)):
    query = db.query(products)
    result = query.all()
    return result
