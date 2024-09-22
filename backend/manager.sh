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
  echo -e "\n\n${redColor}[!] Saliendo...${endColor}\n"
  tput cnorm && exit 1
}

#ctrl_c
trap ctrl_c INT


function helpPanel(){
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Uso:${endColor}"
  echo -e "\t${purpleColor}d)${endColor} ${grayColor}Descargar dependencias del sistema.${endColor}"
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
        echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Instalando $package...${endColor}"
        sudo apt-get install -y $package &> /dev/null
        echo -e "\n${greenColor}[+]${endColor} ${grayColor}$package Instalado con exito.${endColor}"
        sleep 1

    else
        echo -e "\n${greenColor}[+]${endColor} ${grayColor}$package ya está instalado.${endColor}"
        sleep 1
    fi
}


# Indicadores
declare -i parameter_counter=0

# Parametros del script
while getopts "dh" arg; do
  case $arg in
    d) let parameter_counter+=1;; # Instalar dependencias
    h) ;; # Panel de ayuda
  esac
done # Cierre del bucle


# Seleccion de funcion de acuerdo al parametro elegido
if [ $parameter_counter -eq 1 ]; then
  installDependencies
else
  helpPanel
fi # Cierre del condicional
