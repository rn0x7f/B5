from fastapi import APIRouter, Depends, HTTPException
from config.db import conn
from models.user import users
from schemas.user import User
from cryptography.fernet import Fernet
from sqlalchemy.orm import Session
from typing import List
from sqlalchemy.exc import IntegrityError
from config.db import get_db, ENCRYPTION_KEY


f = Fernet(ENCRYPTION_KEY) # Crea un objeto Fernet con la clave generada

user = APIRouter()

# Obtener todos los usuarios
@user.get("/users", response_model=List[User])
async def get_users(db: Session = Depends(get_db)):
    query = db.query(users)  # Realiza la consulta a la tabla de usuarios
    result = query.all()
    return result

# Crear un usuario
@user.post("/users", response_model=User)
async def create_user(user_data: User, db: Session = Depends(get_db)):
    # Crear un diccionario con los datos del usuario a insertar
    new_user = users.insert().values(
        correo_electronico=user_data.correo_electronico,
        nombre=user_data.nombre,
        apellido=user_data.apellido,
        telefono=user_data.telefono,
        contrasena=f.encrypt(user_data.contrasena.encode()).decode()
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

# Obtener un usuario por su correo electrónico
@user.get("/users/{email}", response_model=User)
async def get_user(email: str, db: Session = Depends(get_db)):
    query = db.query(users).filter(users.c.correo_electronico == email)
    result = query.first()
    if result is None:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    return result

# Actualizar un usuario por su correo electrónico
@user.put("/users/{email}", response_model=User)
async def update_user(email: str, user_data: User, db: Session = Depends(get_db)):
    # Crear un diccionario con los datos del usuario a actualizar
    updated_user = {
        "nombre": user_data.nombre,
        "apellido": user_data.apellido,
        "telefono": user_data.telefono,
        "contrasena": f.encrypt(user_data.contrasena.encode()).decode()
    }
    
    # Realizar la actualización en la base de datos
    db.query(users).filter(users.c.correo_electronico == email).update(updated_user)
    db.commit()

# Eliminar un usuario por su correo electrónico
@user.delete("/users/{email}")
async def delete_user(email: str, db: Session = Depends(get_db)):
    # Realizar la eliminación en la base de datos
    db.query(users).filter(users.c.correo_electronico == email).delete()
    db.commit()
    return {"message": "Usuario eliminado"}