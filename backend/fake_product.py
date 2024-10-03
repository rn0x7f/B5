import mysql.connector
from faker import Faker
import random
from dotenv import load_dotenv
import os

load_dotenv()

db_config = {
    'host': os.getenv('MYSQL_IP'),
    'user': os.getenv('MYSQL_USER'),
    'password': os.getenv('MYSQL_PASSWORD'),
    'database': os.getenv('DB_NAME'),
    'port': os.getenv('MYSQL_PORT')
}

db_connection = mysql.connector.connect(**db_config)
cursor = db_connection.cursor()

fake = Faker()

for _ in range(100):
    id_catalogo = 1
    nombre = fake.word().capitalize() + " " + fake.word().capitalize()
    descripcion = fake.sentence()
    precio = round(random.uniform(10.0, 100.0), 2)
    categoria = random.choice(['Electrónica', 'Ropa', 'Alimentos', 'Muebles', 'Juguetes'])
    cantidad = random.randint(1, 50)
    imagen = 'https://picsum.photos/500/500'

    sql = """
    INSERT INTO producto (id_catalogo, nombre, descripcion, precio, categoria, cantidad, imagen)
    VALUES (%s, %s, %s, %s, %s, %s, %s)
    """
    cursor.execute(sql, (id_catalogo, nombre, descripcion, precio, categoria, cantidad, imagen))

db_connection.commit()
cursor.close()
db_connection.close()

print("100 registros insertados correctamente en la tabla 'producto'.")
