from sqlalchemy import create_engine, MetaData
from dotenv import load_dotenv
import os

# Cargar las variables de entorno desde el archivo .env en la raíz del proyecto
load_dotenv('../../.env')  # Dos niveles hacia arriba para llegar al .env

# Obtener las variables de entorno
MYSQL_USER = os.getenv("MYSQL_USER")
MYSQL_PASSWORD = os.getenv("MYSQL_PASSWORD")
DB_NAME = os.getenv("DB_NAME")
MYSQL_PORT = os.getenv("MYSQL_PORT")
MYSQL_IP = os.getenv("MYSQL_IP")

# Crear la URL de conexión
connection_string = f"mysql+pymysql://{MYSQL_USER}:{MYSQL_PASSWORD}@{MYSQL_IP}:{MYSQL_PORT}/{DB_NAME}"

# Crear el motor de la base de datos
engine = create_engine(connection_string)