from sqlalchemy import Table, Column
from sqlalchemy import ForeignKey
from sqlalchemy.sql.sqltypes import String, Integer, Float
from config.db import meta, engine

catalog = Table(
    'catalogo', meta,
    Column('id_catalogo', Integer, primary_key=True, autoincrement=True),
    Column('nombre', String(255)),
    Column('descripcion', String(255))
)

products = Table(
    'producto', meta,
    Column('id_catalogo', Integer, ForeignKey('catalogo.id_catalogo')),
    Column('id_producto', Integer, primary_key=True, autoincrement=True),
    Column('nombre', String(255)),
    Column('descripcion', String(255)),
    Column('precio', Float),
    Column('categoria', String(255)),
    Column('cantidad', Integer),
    Column('imagen', String(255))
)

users = Table(
    'usuario', meta,
    Column('correo_electronico', String(255), primary_key=True),
    Column('nombre', String(30)),
    Column('apellido', String(30)),
    Column('telefono', String(15)),
    Column('contrasena', String(255), nullable=False)
)

meta.create_all(engine)