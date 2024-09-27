from fastapi import APIRouter

auth = APIRouter()

@auth.post("/usuario/signup")
def signup():
    return {"message": "Signup"}

@auth.post("/usuario/signin")
def signin():
    return {"message": "signin"}

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