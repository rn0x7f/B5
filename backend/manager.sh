#!/bin/bash
# @author: rn0x7f / Renato García Morán
# Colors
greenColor="\e[0;32m\033[1m"
redColor="\e[0;31m\033[1m"
blueColor="\e[0;34m\033[1m"
yellowColor="\e[0;33m\033[1m"
purpleColor="\e[0;35m\033[1m"
turquoiseColor="\e[0;36m\033[1m"
grayColor="\e[0;37m\033[1m"
endColor="\033[0m\e[0m"

#ctrl_c
function ctrl_c(){
  echo -e "\n\n${redColor}[!] Abortando...${endColor}\n"
  sleep 1
  tput cnorm && exit 1
}

#ctrl_c
trap ctrl_c INT


# Maneja errores de sudo
# Si se equivoca 3 veces en la contraseña regresa false
check_sudo() {
  tput cnorm # Mostrar cursor
  local command="$1"
  ATTEMPTS=0
  MAX_ATTEMPTS=1
  while [ $ATTEMPTS -lt $MAX_ATTEMPTS ]; do
    if eval "$command"; then
      tput civis # Ocultar cursor
      return 0
    else
      ATTEMPTS=$((ATTEMPTS + 1))
    fi
  done


  echo -e "\n${redColor}[x]${endColor} ${yellowColor}Fallos en la verificación de la contraseña.${endColor}\n"
  
  return 1
}


function help_panel(){
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Uso:${endColor}"
  echo -e "\t${purpleColor}d)${endColor} ${grayColor}Descargar dependencias del sistema.${endColor}"
  echo -e "\t${purpleColor}v)${endColor} ${grayColor}Crear entorno virtual.${endColor}"
  echo -e "\t${purpleColor}u)${endColor} ${grayColor}Registrar un usuario de MySQL.${endColor}"
  echo -e "\t${purpleColor}c)${endColor} ${grayColor}Configurar la base de datos.${endColor}"
  echo -e "\t${purpleColor}k)${endColor} ${grayColor}Crear key de encriptación.${endColor}"
  echo -e "\t${purpleColor}s)${endColor} ${grayColor}Configuración completa.${endColor}"
  echo -e "\t${purpleColor}i)${endColor} ${grayColor}Iniciar proyecto.${endColor}"
  echo -e "\t${purpleColor}h)${endColor} ${grayColor}Mostrar este panel de ayuda.${endColor}\n"
}


# Verifica si un paquete está instalado
check_package() {
  local package="$1"
  dpkg -l | grep -q "$package"
}


# Instala un paquete si no está instalado
install_package() {
  local package="$1"
  if ! check_package "$package"; then
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Instalando $package...${endColor}\n"
    sudo apt-get install -y $package &> /dev/null
    echo -e "${greenColor}[+]${endColor} ${grayColor}$package Instalado con exito.${endColor}\n"
    sleep 1

  else
    echo -e "${greenColor}[+]${endColor} ${grayColor}$package ya está instalado.${endColor}\n"
    sleep 1
  fi
}


# Verifica e instala las dependencias del sistema
install_dependencies() {
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Verificando e instalando dependencias del sistema...${endColor}\n"
  
  # Si falla la autenticación de contraseña no instala dependencias
  if ! check_sudo "sudo apt-get update &>/dev/null"; then
    return 1
  fi

  install_package "build-essential"
  install_package "python3-dev"
  install_package "python3-venv"
  install_package "python3-pip"
  install_package "libmysqlclient-dev"
  install_package "mysql-server"
}


# Crea un entorno virtual de python si no existe
function setup_virtual_env(){
  # Verificar si el módulo venv está disponible en python3
  if ! python3 -c "import venv" &>/dev/null; then
    echo -e "\n${redColor}[!]${endColor} ${grayColor}El módulo 'venv' no está disponible."
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Ejecuta '${endColor}${turquoiseColor}./manager -d${endColor}${grayColor}' para instalar las dependencias necesarias.${endColor}\n"
    return 1
  fi

  # Verificar si ya existe el entorno virtual
  if [ ! -d ".venv" ]; then
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Creando entorno virtual .venv...${endColor}"
    sleep 1
    python3 -m venv .venv
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}Entorno virtual creado.${endColor}\n"
  else
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}El entorno virtual .venv ya existe.${endColor}\n"
  fi
  
  # Subshell para instalar los requerimientos con pip para el venv
  (
    source .venv/bin/activate
    # Instalar dependencias desde requirements.txt si existe
    if [ -f "requirements.txt" ]; then
      echo -e "${yellowColor}[+]${endColor} ${grayColor}Instalando dependencias desde requirements.txt...${endColor}"
      pip install -r requirements.txt &>/dev/null
      echo -e "\n${greenColor}[+]${endColor} ${grayColor}Dependencias instaladas correctamente.${endColor}\n"
    else
      echo -e "${redColor}[!]${endColor} ${yellowColor}Archivo requirements.txt no encontrado. No se han instalado las dependencias.${endColor}\n"
    fi
  )

  echo -e "${yellowColor}[+]${endColor} ${blueColor}Para activar el entorno virtual, ejecuta:${endColor} ${turquoiseColor}source .venv/bin/activate${endColor}\n"
  echo -e "${yellowColor}[+]${endColor} ${blueColor}Para desactivar el entorno virtual, ejecuta:${endColor} ${turquoiseColor}deactivate${endColor}\n"
}


# Crear usuario y base de datos en MySQL
function mysql_account(){
  # Inicia MySQL
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Iniciando MySQL...${endColor}"
  if ! check_sudo "sudo service mysql start &>/dev/null"; then
    return 1
  fi
  sleep 1
  echo -e "\n${greenColor}[+]${endColor} ${grayColor}MySQL Iniciado correctamente.${endColor}\n"
  sleep 1
  # Mensaje enseñando como detener MySQL
  echo -e "${yellowColor}[+]${endColor} ${blueColor}Para detener MySQL, ejecuta:${endColor} ${turquoiseColor}sudo service mysql stop${endColor}\n"

  # Solicitar credenciales al usuario
  echo -e "${redColor}[!]${endColor} ${grayColor}Introduce el nombre de usuario MySQL:${endColor}"
  read -p "" MYSQL_USER
  echo ""
  while true; do
    echo -e "${redColor}[!]${endColor} ${grayColor}Introduce la contraseña para el usuario MySQL:${endColor}"
    read -sp "" MYSQL_PASSWORD
    echo -e "\n${redColor}[!]${endColor} ${grayColor}Verifica la contraseña:${endColor}"
    read -sp "" MYSQL_PASSWORD_CONFIRM
    
    if [ "$MYSQL_PASSWORD" == "$MYSQL_PASSWORD_CONFIRM" ]; then
      break
    else
      echo -e "\n${redColor}[!]${endColor} ${yellowColor}Las contraseñas no coinciden. Por favor, inténtalo de nuevo.${endColor}\n"
    fi
  done
  
  # Antes de crear el usuario verifica si ya existe y pregunta si desea sobreescribirlo
  if sudo mysql -u root -e "SELECT User FROM mysql.user WHERE User='$MYSQL_USER';" | grep -q "$MYSQL_USER"; then
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}El usuario MySQL '$MYSQL_USER' ya existe.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}¿Deseas sobreescribirlo?${endColor} ${turquoiseColor}[s/n]${endColor}"
    read -p "" overwrite
    if [ "$overwrite" == "n" ]; then
      return 1
    else
      sudo mysql -u root -e "DROP USER IF EXISTS '$MYSQL_USER'@'localhost';"
      echo -e "${yellowColor}[+]${endColor} ${grayColor}Usuario ${turquoiseColor}$MYSQL_USER${endColor} eliminado.${endColor}"
    fi
  fi
  sleep 1
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Creando usuario ${turquoiseColor}$MYSQL_USER${endColor}.${endColor}\n"
  sudo mysql -u root -e "CREATE USER '$MYSQL_USER'@'localhost' IDENTIFIED BY '$MYSQL_PASSWORD';"
  sudo mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO '$MYSQL_USER'@'localhost';"
  sudo mysql -u root -e "FLUSH PRIVILEGES;"  
  echo -e "${greenColor}[+]${endColor} ${grayColor}Usuario ${turquoiseColor}$MYSQL_USER${endColor} registrado.${endColor}"
  sleep 1

  # Verifica si el usuario y la clave ya están en el archivo .env y los sobreescribe o los agrega
  if [ -f ".env" ]; then
    if grep -q "MYSQL_USER" .env; then
      sed -i "s/MYSQL_USER=.*/MYSQL_USER=\"$MYSQL_USER\"/g" .env
      sed -i "s/MYSQL_PASSWORD=.*/MYSQL_PASSWORD=\"$MYSQL_PASSWORD\"/g" .env
    else
      echo "MYSQL_USER=\"$MYSQL_USER\"" >> .env
      echo "MYSQL_PASSWORD=\"$MYSQL_PASSWORD\"" >> .env
    fi
  else
    echo "MYSQL_USER=\"$MYSQL_USER\"" >> .env
    echo "MYSQL_PASSWORD=\"$MYSQL_PASSWORD\"" >> .env
  fi

  echo -e "\n${greenColor}[+]${endColor} ${grayColor}Credenciales de MySQL registradas en .env${endColor}\n"
}


# Configurar la base de datos
function database_config(){
  # Verifica si el archivo .env existe y contiene las credenciales de MySQL
  if [ -f ".env" ]; then
    source .env
    if [ -z "$MYSQL_USER" ] || [ -z "$MYSQL_PASSWORD" ]; then
      echo -e "\n${redColor}[!]${endColor} ${yellowColor}No se han encontrado las credenciales de MySQL en el archivo .env.${endColor}\n"
      echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, ejecuta ${turquoiseColor}./manager.sh -u${endColor} para registrar un usuario MySQL.${endColor}\n"
      return 1
    fi
  else
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}No se ha encontrado el archivo .env.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, ejecuta ${turquoiseColor}./manager.sh -u${endColor} ${yellowColor}para registrar un usuario MySQL y crear .env.${endColor}\n"
    return 1
  fi

  # Verifica si el usuario y la clave son correctos
  if ! mysql -u $MYSQL_USER -p$MYSQL_PASSWORD -e "SELECT 1" &>/dev/null; then
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}Las credenciales de MySQL son incorrectas.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, ejecuta ${turquoiseColor}./manager.sh -u${endColor} ${yellowColor}para registrar un usuario MySQL.${endColor}\n"
    return 1
  fi

  # Verifica si el archivo database.sql existe
  if [ ! -f "database.sql" ]; then
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}No se ha encontrado el archivo database.sql.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, asegúrate de que el archivo database.sql esté en la carpeta actual.${endColor}\n"
    sleep 1
    return 1
  fi
  # Lee el nombre de la base de datos desde database.sql
  DB_NAME=$(grep -oP '(?<=CREATE DATABASE IF NOT EXISTS )\w+' database.sql)
  # Verifica que se encuentre el nombre de la base de datos
  if [ -z "$DB_NAME" ]; then
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}No se ha encontrado el nombre de la base de datos en database.sql.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, asegúrate de que el archivo database.sql contenga el nombre de la base de datos.${endColor}\n"
    # Muestra ejemplo de donde debe ir el nombre de la base de datos
    echo -e "${yellowColor}[+]${endColor} ${grayColor}Ejemplo:${endColor}"
    echo -e "\t${turquoiseColor}CREATE DATABASE IF NOT EXISTS nombre_de_la_base_de_datos;${endColor}\n"
    return 1
  fi

  # Guarda o actualiza el nombre de la base de datos en el archivo .env
  if grep -q "^DB_NAME=" .env; then
    # Si ya existe, la actualiza
    sed -i "s/^DB_NAME=.*/DB_NAME=$DB_NAME/" .env
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}Nombre de la base de datos actualizado en .env.${endColor}\n"
  else
    # Si no existe, la agrega
    echo "DB_NAME=$DB_NAME" >> .env
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}Nombre de la base de datos guardado en .env.${endColor}\n"
  fi

  # Verifica si ya existe la base de datos y pregunta si se desea eliminar
  if mysql -u $MYSQL_USER -p$MYSQL_PASSWORD -e "SHOW DATABASES;" 2>/dev/null | grep -q "$DB_NAME"; then
    echo -e "${redColor}[!]${endColor} ${yellowColor}La base de datos '$DB_NAME' ya existe.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}¿Deseas eliminarla?${endColor} ${turquoiseColor}[s/n]${endColor}"
    read -p "" delete_db
    if [ "$delete_db" == "n" ]; then
      return 1
    else
      mysql -u $MYSQL_USER -p$MYSQL_PASSWORD -e "DROP DATABASE IF EXISTS $DB_NAME;" 2>/dev/null
      echo -e "\n${redColor}[+] Base de datos $DB_NAME eliminada.${endColor}"
    fi
  fi
  
  # Crea la base de datos
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Creando base de datos...${endColor}"
  sleep 1

  mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS $DB_NAME;" &>/dev/null

  echo -e "\n${greenColor}[+]${endColor} ${grayColor}Base de datos $DB_NAME creada.${endColor}"
  sleep 1

  # Pregunta al usuario si desea ejecutar el script
  echo -e "\n${redColor}[!]${endColor} ${yellowColor}¿Deseas ejecutar el script database.sql para crear las tablas?${endColor} ${turquoiseColor}[s/n]${endColor}"
  read -p "" respuesta

  if [[ "$respuesta" == "s" ]]; then
      # Ejecuta el script para crear las tablas
      mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" < database.sql &>/dev/null
      echo -e "\n${greenColor}[+] Base de datos y tablas creadas exitosamente.${endColor}"
  else
      echo -e "\n${yellowColor}[-] No se han creado las tablas.${endColor}"
  fi


  # Verifica en que puerto corre mysql y lo guarda en .env
  MYSQL_PORT=$(mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SHOW VARIABLES LIKE 'port';" 2>/dev/null | awk '{print $2}' | tail -n 1)

  # Guarda o actualiza el puerto en el archivo .env
  if grep -q "^MYSQL_PORT=" .env; then
    # Si ya existe, la actualiza
    sed -i "s/^MYSQL_PORT=.*/MYSQL_PORT=$MYSQL_PORT/" .env
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}Puerto de MySQL actualizado en .env.${endColor}\n"
    sleep 1
  else
    # Si no existe, la agrega
    echo "MYSQL_PORT=$MYSQL_PORT" >> .env
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}Puerto de MySQL guardado en .env.${endColor}\n"
    sleep 1
  fi

  # Verifica en que dirección IP corre mysql y lo guarda en .env
  MYSQL_IP=$(mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SHOW VARIABLES LIKE 'bind_address';" 2>/dev/null | awk '{print $2}' | tail -n 1)
  if grep -q "^MYSQL_IP=" .env; then
    # Si ya existe, la actualiza
    sed -i "s/^MYSQL_IP=.*/MYSQL_IP=$MYSQL_IP/" .env
    echo -e "${greenColor}[+]${endColor} ${grayColor}IP de MySQL actualizado en .env.${endColor}\n"
    sleep 1
  else
    # Si no existe, la agrega
    echo "MYSQL_IP=$MYSQL_IP" >> .env
    echo -e "${greenColor}[+]${endColor} ${grayColor}IP de MySQL guardado en .env.${endColor}\n"
    sleep 1
  fi

}


function setup_key(){
  # Verifica si el usuario está corriendo el script en un entorno virtual
  if [ -z "$VIRTUAL_ENV" ]; then
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}No estás en un entorno virtual.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, ejecuta ${turquoiseColor}source .venv/bin/activate${endColor} ${yellowColor}para activar el entorno virtual.${endColor}\n"
    return 1
  fi

  # Verifica si se tiene el módulo secrets, que está disponible en Python 3 de manera predeterminada
  if ! python3 -c "import secrets" &>/dev/null; then
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}El módulo 'secrets' no está disponible.${endColor}\n"
    echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, asegúrate de tener Python 3 instalado.${endColor}\n"
    return 1
  fi

  if [ -f ".env" ]; then
    source .env
    if [ -z "$SECRET_KEY" ]; then
      echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Generando SECRET_KEY...${endColor}"
      sleep 1
      SECRET_KEY=$(python3 -c "import secrets; print(secrets.token_hex(32))")
      echo -e "\n${greenColor}[+]${endColor} ${grayColor}SECRET_KEY generada.${endColor}"
      sleep 1
      echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Guardando SECRET_KEY en .env...${endColor}"
      echo "SECRET_KEY=\"$SECRET_KEY\"" >> .env
      echo -e "\n${greenColor}[+] SECRET_KEY guardada en .env.${endColor}\n"
      sleep 1
    else
      echo -e "\n${redColor}[!]${endColor} ${yellowColor}La SECRET_KEY ya existe en el archivo .env.${endColor}\n"
      echo -e "${redColor}[!]${endColor} ${yellowColor}Si deseas generar una nueva SECRET_KEY, elimina la clave actual del archivo .env.${endColor}\n"
      echo -e "\t${redColor}Borrar:${endColor} ${grayColor}SECRET_KEY=\"abcdef\"${endColor}\n"
      echo -e "${redColor}[!]${endColor} ${redColor}Si generas una nueva SECRET_KEY, los tokens generados previamente dejarán de ser válidos.${endColor}\n"
      return 1
    fi
  else
    # Si no existe el archivo .env, lo crea
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Generando SECRET_KEY...${endColor}"
    sleep 1
    SECRET_KEY=$(python3 -c "import secrets; print(secrets.token_hex(32))")
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}SECRET_KEY generada.${endColor}"
    sleep 1
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Guardando SECRET_KEY en .env...${endColor}"
    echo "SECRET_KEY=\"$SECRET_KEY\"" >> .env
    echo -e "\n${greenColor}[+] SECRET_KEY guardada en .env.${endColor}\n"
    sleep 1
    return 1
  fi
}


function init_project(){
  if [ -f ".env" ] && [ -d ".venv" ]; then
    source .env
    if [ -z "$SECRET_KEY" ] || [ -z "$MYSQL_USER" ] || [ -z "$MYSQL_PASSWORD" ] || [ -z "$DB_NAME" ] || [ -z "$MYSQL_PORT" ] || [ -z "$MYSQL_IP" ] ; then
      echo -e "\n${redColor}[!]${endColor} ${yellowColor}No se han encontrado las credenciales necesarias en el archivo .env.${endColor}\n"
      echo -e "\t${redColor}"
      echo -e "${redColor}[!]${endColor} ${yellowColor}Ejecuta ${turquoiseColor}./manager.sh -u${endColor} ${yellowColor}para registrar un usuario MySQL.${endColor}\n"
      echo -e "${redColor}[!]${endColor} ${yellowColor}Ejecuta ${turquoiseColor}./manager.sh -c${endColor} ${yellowColor}para Configurar la base de datos.${endColor}\n"
      echo -e "${redColor}[!]${endColor} ${yellowColor}Ejecuta ${turquoiseColor}./manager.sh -k${endColor} ${yellowColor}para Crear la key de encriptación.${endColor}\n"
      return 1
    else
      # Verifica si el usuario está corriendo el script en un entorno virtual
      if [ -z "$VIRTUAL_ENV" ]; then
        echo -e "\n${redColor}[!]${endColor} ${yellowColor}No estás en un entorno virtual.${endColor}\n"
        echo -e "${redColor}[!]${endColor} ${yellowColor}Por favor, ejecuta ${turquoiseColor}source .venv/bin/activate${endColor} ${yellowColor}para activar el entorno virtual.${endColor}\n"
        return 1
      else 
        # Inicia el proyecto
        echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Iniciando proyecto...${endColor}"
        sleep 1
        echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Para cerrar el proyecto, presiona ${turquoiseColor}Ctrl + C${endColor}${endColor}\n"
        sleep 1
        uvicorn app:app --reload --app-dir api/
      fi
    fi
  else
    echo -e "\n${redColor}[!]${endColor} ${yellowColor}No se han encontrado los archivos .env / .venv${endColor}"
    echo -e "\n${yellowColor}[+]${endColor} Antes de iniciar el proyecto, utiliza los otros parámetros para configurar el entorno.${endColor}\n"
    return 1
  fi
}

# Indicadores
declare -i parameter_counter=0

# Parametros del script
while getopts "dvucksih" arg; do
  case $arg in
    d) let parameter_counter+=1;; # Instalar dependencias
    v) let parameter_counter+=2;; # Crear entorno virtual
    u) let parameter_counter+=3;; # Crear usuario MySQL
    c) let parameter_counter+=4;; # Configurar base de datos
    k) let parameter_counter+=5;; # Crear key de encriptación
    s) let parameter_counter+=6;; # Setup completo
    i) let parameter_counter+=7;; # Iniciar proyecto
    h) ;; # Panel de ayuda
  esac
done # Cierre del bucle


tput civis # Ocultar cursor
# Seleccion de funcion de acuerdo al parametro elegido
if [ $parameter_counter -eq 1 ]; then
  install_dependencies
elif [ $parameter_counter -eq 2 ]; then
  setup_virtual_env
elif [ $parameter_counter -eq 3 ]; then
  mysql_account
elif [ $parameter_counter -eq 4 ]; then
  database_config
elif [ $parameter_counter -eq 5 ]; then
  setup_key
elif [ $parameter_counter -eq 6 ]; then
  install_dependencies
  setup_virtual_env
  mysql_account
  database_config
  setup_key
elif [ $parameter_counter -eq 7 ]; then
  init_project
else
  help_panel
fi # Cierre del condicional
tput cnorm # Mostrar cursor
