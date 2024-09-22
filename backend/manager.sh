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
  echo -e "\n\n${redColour}[!] Saliendo...${endColour}\n"
  tput cnorm && exit 1
}

#ctrl_c
trap ctrl_c INT


function helpPanel(){
  echo -e "\n${yellowColor}[+]${endColor} ${grayColor}Uso:${endColor}"
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
