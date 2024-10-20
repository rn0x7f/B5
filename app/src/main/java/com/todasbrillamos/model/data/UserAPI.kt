package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz de servicio que define las operaciones relacionadas con los usuarios usando Retrofit.
 */
interface UserAPI {
    /**
     * Obtiene la información de un usuario basado en su correo electrónico.
     *
     * @param email El correo electrónico del usuario cuyo información se desea obtener.
     * @return Respuesta que contiene la información del usuario solicitado.
     */
    @GET("/api/users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserInfo>

    /**
     * Actualiza la información de un usuario basado en su correo electrónico.
     *
     * @param email El correo electrónico del usuario cuyo información se desea actualizar.
     * @param userInfo Objeto que contiene la nueva información del usuario.
     * @return Respuesta que contiene la información actualizada del usuario.
     */
    @PUT("/api/users/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body userInfo: DataChangeRequest): Response<UserInfo>
}

/**
 * Data class que representa la solicitud para cambiar la información de un usuario.
 *
 * @property correo_electronico El nuevo correo electrónico del usuario.
 * @property nombre El nuevo nombre del usuario.
 * @property apellido El nuevo apellido del usuario.
 * @property telefono El nuevo número de teléfono del usuario.
 * @property contrasena La nueva contraseña del usuario.
 */
data class DataChangeRequest(
    val correo_electronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val contrasena: String
)