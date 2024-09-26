from sqlalchemy import create_engine, MetaData
from dotenv import load_dotenv
import os
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker


# Dos niveles hacia arriba para llegar al .env
# Cargar las variables de entorno desde el archivo .env en la raíz del proyecto
dotenv_path = os.path.join(os.path.dirname(__file__), '..', '..', '.env')
load_dotenv(dotenv_path)

# Obtener las variables de entorno
MYSQL_USER = os.getenv("MYSQL_USER")
MYSQL_PASSWORD = os.getenv("MYSQL_PASSWORD")
DB_NAME = os.getenv("DB_NAME")
MYSQL_PORT = os.getenv("MYSQL_PORT")
MYSQL_IP = os.getenv("MYSQL_IP")
SECRET_KEY = os.getenv("SECRET_KEY")

# Crear la URL de conexión
connection_string = f"mysql+pymysql://{MYSQL_USER}:{MYSQL_PASSWORD}@{MYSQL_IP}:{MYSQL_PORT}/{DB_NAME}"

# Crear el motor de la base de datos
engine = create_engine(connection_string)

# Para manejar sesiones
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()