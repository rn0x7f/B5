package com.todasbrillamos.model.data

/**
 * Data class que representa la información de un usuario.
 *
 * @property correo_electronico El correo electrónico del usuario.
 * @property nombre El nombre del usuario.
 * @property apellido El apellido del usuario.
 * @property telefono El número de teléfono del usuario.
 */
data class UserInfo(
    val correo_electronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String
) {
    /**
     * Devuelve una representación en cadena del usuario, mostrando su nombre y apellido.
     *
     * @return Una cadena en el formato "nombre apellido".
     */
    override fun toString(): String {
        return "$nombre $apellido"
    }
}