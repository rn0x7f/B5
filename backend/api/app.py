from fastapi import FastAPI
from routes.user import user
from routes.auth import auth
from routes.product import product

app = FastAPI()

app.include_router(user)
app.include_router(auth, prefix="/api/auth", tags=["auth"])
app.include_router(product, prefix="/api/products", tags=["products"])