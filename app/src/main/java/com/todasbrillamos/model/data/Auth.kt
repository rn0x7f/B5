package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Data class que representa la solicitud de inicio de sesión
 *
 * @property email El correo electrónico del usuario
 * @property password La contraseña del usuario.
 */
data class SignInRequest(
    val email: String,
    val password: String
)

/**
 * Data class que representa la solicitud de registro (Signup).
 *
 * @property correo_electronico El correo electrónico del usuario.
 * @property nombre El nombre del usuario.
 * @property apellido El apellido del usuario.
 * @property telefono El número de teléfono del usuario.
 * @property contrasena La contraseña elegida por el usuario.
 */
data class SignupRequest(
    val correo_electronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val contrasena: String
)

/**
 * Data class que representa la respuesta después de un registro exitoso (Signup).
 *
 * @property email El correo electrónico registrado.
 * @property name El nombre del usuario registrado.
 * @property lastName El apellido del usuario registrado.
 * @property phone El número de teléfono del usuario registrado.
 * @property password La contraseña registrada.
 */
data class SignupResponse(
    val email: String,
    val name: String,
    val lastName: String,
    val phone: String,
    val password: String
)

/**
 * Data class que representa la respuesta del servidor al iniciar sesión con éxito (SignIn).
 *
 * @property token El token de autenticación recibido tras un inicio de sesión exitoso.
 */
data class TokenResponse(
    val token: String
)

/**
 * Interfaz de servicio que define las operaciones de autenticación usando Retrofit.
 */
interface Auth {
    /**
     * Realiza una solicitud de registro (Signup) de un usuario en el servidor.
     *
     * @param request Objeto que contiene la información necesaria para registrar un nuevo usuario.
     * @return Respuesta que incluye los detalles del usuario registrado.
     */
    @POST("/api/auth/usuario/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<SignupResponse>

    /**
     * Realiza una solicitud de inicio de sesión (SignIn) de un usuario en el servidor.
     *
     * @param request Objeto que contiene las credenciales del usuario para iniciar sesión.
     * @return Respuesta que incluye el token de autenticación del usuario.
     */
    @POST("/api/auth/usuario/signin")
    suspend fun signin(
        @Body request: SignInRequest
    ): Response<TokenResponse>

}
