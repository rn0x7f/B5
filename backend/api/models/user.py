from sqlalchemy import Table, Column
from sqlalchemy.sql.sqltypes import String
from config.db import meta, engine

users = Table(
    'usuario', meta,
    Column('correo_electronico', String(255), primary_key=True),
    Column('nombre', String(30)),
    Column('apellido', String(30)),
    Column('telefono', String(15)),
    Column('contrasena', String(255), nullable=False)
)

meta.create_all(engine)  # Crear la tabla en la base de datos