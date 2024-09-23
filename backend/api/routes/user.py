from fastapi import APIRouter
from config.db import conn
from models.user import users

user = APIRouter()

# Obtener todos los usuarios
@user.get("/users")
def get_users():
  return conn.execute(users.select()).fetchall() # Hace una consulta de todos los usuarios en la tabla
