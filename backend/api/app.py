from fastapi import FastAPI
from routes.usuario import usuario
from routes.auth import auth
from routes.producto import producto

app = FastAPI()

app.include_router(usuario, prefix="/api/users", tags=["users"])
app.include_router(producto, prefix="/api/products", tags=["products"])
app.include_router(auth, prefix="/api/auth", tags=["auth"])