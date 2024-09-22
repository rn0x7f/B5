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
  echo -e "\t${purpleColor}m)${endColor} ${grayColor}Registrar un usuario de MySQL.${endColor}"
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


# Indicadores
declare -i parameter_counter=0

# Parametros del script
while getopts "dvmh" arg; do
  case $arg in
    d) let parameter_counter+=1;; # Instalar dependencias
    v) let parameter_counter+=2;; # Crear entorno virtual
    m) let parameter_counter+=4;; # Crear usuario MySQL
    h) ;; # Panel de ayuda
  esac
done # Cierre del bucle


tput civis # Ocultar cursor
# Seleccion de funcion de acuerdo al parametro elegido
if [ $parameter_counter -eq 1 ]; then
  install_dependencies
elif [ $parameter_counter -eq 2 ]; then
  setup_virtual_env
elif [ $parameter_counter -eq 4 ]; then
  mysql_account
else
  help_panel
fi # Cierre del condicional
tput cnorm # Mostrar cursor
