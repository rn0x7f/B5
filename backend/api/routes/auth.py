from fastapi import APIRouter, Depends
from typing import Annotated
from pydantic import EmailStr
from utils import authAcc
from sqlalchemy.orm import Session
from schemas.schemas import Usuario, UsuarioCreate
from config.db import SessionLocal

auth = APIRouter()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@auth.post("/usuario/verify-token")
def verify_token(token: str, db: Session = Depends(get_db)):
    if authAcc.is_valid_token(db, token):
        return {"status": "OK"}
    return {"status": "Invalid token"}

@auth.post("/usuario/signup")
def signup(usuario: UsuarioCreate, db: Session = Depends(get_db)):
    return authAcc.signup(db, usuario)

@auth.post("/usuario/signin")
def signin(email: Annotated[EmailStr, "email"], password: Annotated[str, "password"], db: Session = Depends(get_db)):
    return authAcc.signin(db, email, password)

@auth.post("/usuario/signout")
def signout():
    return {"message": "signout"}

@auth.post("/usuario/forgot-password")
def forgot_password():
    return {"message": "Forgot password"}

@auth.post("/usuario/reset-password")
def reset_password():
    return {"message": "Reset password"}

#________________________________________________________

@auth.post("/admin/signup")
def admin_signup():
    return {"message": "Admin Signup"}

@auth.post("/admin/signin")
def admin_signin():
    return {"message": "Admin Signin"}

@auth.post("/admin/signout")
def admin_signout():
    return {"message": "Admin Signout"}

@auth.post("/admin/forgot-password")
def admin_forgot_password():
    return {"message": "Admin Forgot password"}

@auth.post("/admin/reset-password")
def admin_reset_password():
    return {"message": "Admin Reset password"}