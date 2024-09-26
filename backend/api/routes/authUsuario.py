from fastapi import APIRouter

authUsuario = APIRouter()

@authUsuario.post("/signup")
def signup():
    return {"message": "Signup"}