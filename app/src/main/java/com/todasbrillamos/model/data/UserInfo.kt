package com.todasbrillamos.model.data

data class UserInfo(
    val correo_electronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String
) {
    override fun toString(): String {
        return "$nombre $apellido"
    }

}

