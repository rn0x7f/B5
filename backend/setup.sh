#!/bin/bash

# Colors
greenColor="\e[0;32m\033[1m"
redColor="\e[0;31m\033[1m"
blueColor="\e[0;34m\033[1m"
yellowColor="\e[0;33m\033[1m"
purpleColor="\e[0;35m\033[1m"
turquoiseColor="\e[0;36m\033[1m"
grayColor="\e[0;37m\033[1m"
endColor="\033[0m\e[0m"

# Configura la base de datos
data_base() {
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Iniciando MySQL...${endColor}"
    if ! check_sudo "sudo service mysql start &>/dev/null"; then
            return 1
    fi
    sleep 1
    echo -e "\n${greenColor}[+]${endColor} ${grayColor}MySQL Iniciado correctamente.${endColor}\n"
    
    # Solicitar credenciales al usuario
    read -p "Introduce el nuevo usuario de MySQL: " MYSQL_USER
    echo ""
    while true; do
        read -sp "Introduce la contraseña para el usuario MySQL: " MYSQL_PASSWORD
        echo -e "\n"
        read -sp "Verifica la contraseña: " MYSQL_PASSWORD_CONFIRM
        echo -e "\n"
        
        
        if [ "$MYSQL_PASSWORD" == "$MYSQL_PASSWORD_CONFIRM" ]; then
            break
        else
            echo -e "\n${redColor}[!]${endColor} ${grayColor}Las contraseñas no coinciden. Por favor, inténtalo de nuevo.${endColor}"
        fi
    done


    # Crear el nuevo usuario y darle permisos
    sudo mysql -u root -e "CREATE USER '$MYSQL_USER'@'localhost' IDENTIFIED BY '$MYSQL_PASSWORD';"
    sudo mysql -u root -e "GRANT ALL PRIVILEGES ON *.* TO '$MYSQL_USER'@'localhost';"
    sudo mysql -u root -e "FLUSH PRIVILEGES;"

    # Agregar las credenciales al .bashrc
    echo "export MYSQL_USER=\"$MYSQL_USER\"" >> ~/.bashrc
    echo "export MYSQL_PASSWORD=\"$MYSQL_PASSWORD\"" >> ~/.bashrc

    echo -e "${greenColor}[+]${endColor} ${grayColor}Usuario nuevo de MySQL registrado.${endColor}"
    
    sleep 1

    # Aplicar cambios en el .bashrc
    source ~/.bashrc

    # Cambiar la contraseña del usuario root
    # sudo mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '$MYSQL_PASSWORD';" 2>/dev/null
    
    # echo -e "\n${greenColor}[+]${endColor} ${grayColor}Contraseña del usuario root actualizada.${endColor}"

    DB_NAME="TodasBrillamos"
    # Crear una base de datos
    CREATE_DB_OUTPUT=$(mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE DATABASE $DB_NAME;" 2>&1)

    if [ $? -eq 0 ]; then
        echo -e "\n${greenColor}[+]${endColor} ${grayColor}Base de datos '$DB_NAME' creada correctamente.${endColor}"
        sleep 1
        echo -e "\n${greenColor}[+]${endColor} ${grayColor}Puedes revisar las credenciales en tu archivo ~/.bashrc.${endColor}"
        sleep 2
    else
        echo -e "\n${redColor}[!]${endColor} ${grayColor}No se pudo crear la base de datos '${DB_NAME}'.${endColor}"
        sleep 2
    fi
}


# Verifica si Python está instalado
check_python() {
    if ! command -v python3 &> /dev/null; then
        echo -e "\n${yellowColor}[x]${endColor} ${redColor}Python no está instalado. Por favor, instala Python 3 y verifica que está en el PATH antes de continuar.${endColor}\n"
        sleep 10
        return 1
    fi
    return 0
}

# Barra de progreso
show_progress() {
    local message="$1"
    local duration="$2"
    local width=30
    local start_time=$(date +%s)
    local end_time=$((start_time + duration))
    
    echo -n "$message ["
    while [ $(date +%s) -lt $end_time ]; do
        local elapsed=$(( $(date +%s) - start_time ))
        local percent=$(( elapsed * 100 / duration ))
        local filled=$(( percent * width / 100 ))
        local empty=$(( width - filled ))
        printf "\r${message} [${greenColor}$(printf "%${filled}s" | tr ' ' '#')${endColor}$(printf "%${empty}s" | tr ' ' '.')]" 
        sleep 0.1
    done
    echo -e "\r${message} [${greenColor}$(printf "%${width}s" | tr ' ' '#')${endColor}] Done!"
}

# Verifica si un paquete está instalado
check_package() {
    local package="$1"
    dpkg -l | grep -q "$package"
}

# Maneja errores de sudo
# Si se equivoca 6 veces en la contraseña regresa false
check_sudo() {
    local command="$1"
    ATTEMPTS=0
    MAX_ATTEMPTS=2
    while [ $ATTEMPTS -lt $MAX_ATTEMPTS ]; do
        if eval "$command"; then
            return 0
        else
            ATTEMPTS=$((ATTEMPTS + 1))
        fi
    done

    echo -e "\n${redColor}[x]${endColor} ${yellowColor}Fallos en la verificación de la contraseña. Vuelve a correr el script.${endColor}\n"
    
    return 1
}

# Instala una dependencia si no está instalada
install_package() {
    local package="$1"
    if ! check_package "$package"; then
        echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Instalando $package...${endColor}"
        sudo apt-get install -y $package &> /dev/null
        echo -e "\n${greenColor}[+]${endColor} ${grayColor}$package Instalado con exito.${endColor}"
        sleep 1

    else
        echo -e "\n${greenColor}[+]${endColor} ${grayColor}$package ya está instalado.${endColor}"
        sleep 1
    fi
}

# Verifica e instala las dependencias del sistema
check_dependencies() {
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Verificando e instalando dependencias del sistema...${endColor}"
    
    # Si falla la autenticación de contraseña no instala dependencias
    if ! check_sudo "sudo apt-get update &>/dev/null"; then
            return 1
    fi

    install_package "build-essential"
    install_package "python3-dev"
    install_package "libmysqlclient-dev"
    install_package "mysql-server"
}

# Manejar caso de tener un venv activo
check_virtual_env() {
    if [ -n "$VIRTUAL_ENV" ]; then
        echo -e "\n${yellowColor}[!]${endColor} ${redColor}Ya estás en un entorno virtual.${endColor}"
        read -p "¿Quieres cerrar el entorno virtual actual y abrir el nuevo? (yes/no) " response
        case "$response" in
            yes|y|s|si)
                echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Cerrando el entorno virtual actual...${endColor}"
                show_progress "" 3
                deactivate
                sleep 1
                activate_virtual_env
                ;;
            no|n)
                echo -e "\n${yellowColor}[x]${endColor} ${redColor}Primero cierra el entorno virtual con${endColor} ${turquoiseColor}deactivate${endColor} ${redColor}antes de ejecutar este script.${endColor}\n"
                ;;
            *)
                echo -e "\n${yellowColor}[x]${endColor} ${redColor}Respuesta no válida.${endColor}\n"
                ;;
        esac
        return 1
    else
        return 0
    fi
}

# Funcion para activar el entorno virtual
activate_virtual_env() {
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Iniciando entorno virtual de Python...${endColor}"
    show_progress "" 4
    source .venv/bin/activate
    echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Para salir del entorno virtual, escribe${endColor} ${turquoiseColor}deactivate${endColor}\n"
}

# Verifica si el entorno virtual ya existe y actúa en consecuencia
setup_virtual_env() {
    if [ ! -d ".venv" ]; then
        
        # Si fallo la autenticación de contraseña regresa false y el script termina
        # Sin crear el entorno virtual, solo pasa cuando no tenemos el .venv
        if ! check_dependencies; then
            return 1
        fi


        if ! data_base; then
            return 1
        fi
        
        echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Creando el entorno virtual...${endColor}"
        python3 -m venv .venv
        echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Instalando dependencias en el entorno virtual...${endColor}"
        sleep 1
        # Usa un subshell para activar el entorno virtual e instalar las dependencias en el venv
        (
            source .venv/bin/activate
            pip install -r requirements.txt &>/dev/null
            echo -e "\n${greenColor}[+]${endColor} ${grayColor}Paquetes de requirements.txt instalados con exito.${endColor}\n"
            sleep 1
        )
        echo -e "${yellowColor}[+]${endColor} ${grayColor}Para iniciar el entorno virtual ejecuta este script una vez más.${endColor}\n"
        return 1
    fi
    return 0
}

# Revisa si el script se ejecuta con source
if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    echo -e "\n${yellowColor}[!]${endColor} ${redColor}Ejecuta el script mediante${endColor} ${turquoiseColor}source${endColor}"
    echo -e "\n${yellowColor}[x]${endColor} ${blueColor}source ./setup.sh${endColor}\n"
else
    if check_python; then
        if setup_virtual_env; then
            if check_virtual_env; then
                activate_virtual_env
            fi
        fi
    fi
fi