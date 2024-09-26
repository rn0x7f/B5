from fastapi import FastAPI
# from routes.user import user
# from routes.auth import auth
# from routes.product import product
from routes.usuario import usuario

app = FastAPI()

app.include_router(usuario)