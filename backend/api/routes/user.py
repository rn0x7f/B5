from fastapi import APIRouter, Depends, HTTPException
#from config.db import conn
from models.user import users
from schemas.user import User
from cryptography.fernet import Fernet
from sqlalchemy.orm import Session
from typing import List
from sqlalchemy.exc import IntegrityError
from config.db import get_db


key = Fernet.generate_key() # Genera string aleatorio para encriptar
f = Fernet(key) # Crea un objeto Fernet con la clave generada

user = APIRouter()

# Obtener todos los usuarios
@user.get("/users", response_model=List[User])
async def get_users(db: Session = Depends(get_db)):
    query = db.query(users)  # Realiza la consulta a la tabla de usuarios
    result = query.all()
    return result

@user.post("/users", response_model=User)
async def create_user(user_data: User, db: Session = Depends(get_db)):
    # Crear un diccionario con los datos del usuario a insertar
    new_user = users.insert().values(
        correo_electronico=user_data.correo_electronico,
        nombre=user_data.nombre,
        apellido=user_data.apellido,
        telefono=user_data.telefono,
        contrasena=user_data.contrasena  # Asegúrate de encriptar la contraseña en producción
    )
    
    try:
        # Ejecutar la inserción en la base de datos
        db.execute(new_user)
        db.commit()  # Confirmar la transacción
    except IntegrityError:
        db.rollback()  # Revertir en caso de error
        raise HTTPException(status_code=400, detail="El correo electrónico ya está registrado.")
    
    # Devolver los datos del usuario creado
    return user_data


# Obtener todos los usuarios
# @user.get("/users")
# def get_users():
#   return conn.execute(users.select()).fetchall() # Hace una consulta de todos los usuarios en la tabla

# @user.post("/users")
# def create_user(user: User):
#   new_user = {
#               "correo_electronico": user.correo_electronico,
#               "nombre": user.nombre,
#               "apellido": user.apellido,
#               "telefono": user.telefono,
#             }
#   new_user["contrasena"] = f.encrypt(user.contrasena.encode()).decode() # Encripta la contraseña
#   result = conn.execute(users.insert().values(new_user)) # Inserta el nuevo usuario en la tabla
#   return conn.execute(users.select().where(users.c.correo_electronico == result.lastrowid)).first() # Devuelve el usuario creado