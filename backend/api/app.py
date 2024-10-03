from fastapi import FastAPI
from routes.usuario import usuario
from routes.auth import auth
from routes.producto import producto
from routes.catalogo import catalogo
from routes.stripe import stripe_route
from routes.carrito import carrito

app = FastAPI()

app.include_router(usuario, prefix="/api/users", tags=["users"])
app.include_router(catalogo, prefix="/api/catalogs", tags=["catalogs"])
app.include_router(producto, prefix="/api/products", tags=["products"])
app.include_router(carrito, prefix="/api/carts", tags=["carts"])
app.include_router(auth, prefix="/api/auth", tags=["auth"])
app.include_router(stripe_route, prefix="/api/stripe", tags=["stripe"])