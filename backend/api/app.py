from fastapi import FastAPI
from routes.usuario import usuario
from routes.authUsuario import authUsuario

app = FastAPI()

app.include_router(usuario, prefix="/api/users", tags=["users"])
app.include_router(authUsuario, prefix="/api/auth", tags=["auth"])
