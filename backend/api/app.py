from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from routes.usuario import usuario
from routes.auth import auth
from routes.producto import producto
from routes.catalogo import catalogo
from routes.stripe import stripe_route
from routes.carrito import carrito
from routes.compra import compra

app = FastAPI()

origins = [
    "http://localhost:3000",
    "http://localhost:3001"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

app.include_router(usuario, prefix="/api/users", tags=["users"])
app.include_router(catalogo, prefix="/api/catalogs", tags=["catalogs"])
app.include_router(producto, prefix="/api/products", tags=["products"])
app.include_router(carrito, prefix="/api/carts", tags=["carts"])
app.include_router(compra, prefix="/api/purchases", tags=["purchases"])
app.include_router(auth, prefix="/api/auth", tags=["auth"])
app.include_router(stripe_route, prefix="/api/stripe", tags=["stripe"])