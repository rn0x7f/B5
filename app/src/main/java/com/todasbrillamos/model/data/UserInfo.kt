package com.todasbrillamos.model.data

data class UserInfo(
    val correo_electronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val direcciones_envio: List<String>,
    val compras: List<String>
) {
    override fun toString(): String {
        return "$nombre $apellido"
    }

    fun withUpdatedTelefono(newTelefono: String): UserInfo {
        return this.copy(telefono = newTelefono)
    }

    fun withUpdatedNombre(newNombre: String): UserInfo {
        return this.copy(nombre = newNombre)
    }

    fun withUpdatedApellido(newApellido: String): UserInfo {
        return this.copy(apellido = newApellido)
    }

    fun withUpdatedCorreo(correo: String): UserInfo {
        return this.copy(correo_electronico = correo)
    }

    fun withUpdatedDireccionesEnvio(newDirecciones: List<String>): UserInfo {
        return this.copy(direcciones_envio = newDirecciones)
    }

    fun withUpdatedCompras(newCompras: List<String>): UserInfo {
        return this.copy(compras = newCompras)
    }
}

