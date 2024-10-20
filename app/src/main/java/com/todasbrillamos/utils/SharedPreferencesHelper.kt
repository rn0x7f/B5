package com.todasbrillamos.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase que proporciona métodos para almacenar y recuperar datos del almacenamiento local utilizando SharedPreferences.
 *
 * @param context Contexto de la aplicación para acceder a SharedPreferences.
 */
class SharedPreferencesHelper(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_prefs" // Nombre del archivo de preferencias
        private const val TOKEN_KEY = "auth_token" // Clave para el token de autenticación
        private const val EMAIL_KEY = "user_email" // Clave para el correo electrónico del usuario
    }

    // Instancia de SharedPreferences
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Guarda el token de autenticación localmente.
     *
     * @param token El token de autenticación a almacenar.
     */
    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply() // Aplica los cambios de forma asíncrona
    }

    /**
     * Recupera el token de autenticación almacenado.
     *
     * @return El token de autenticación o null si no existe.
     */
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    /**
     * Elimina el token de autenticación (para propósitos de cierre de sesión).
     */
    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove(TOKEN_KEY) // Remueve el token
        editor.apply() // Aplica los cambios de forma asíncrona
    }

    /**
     * Guarda el correo electrónico del usuario localmente.
     *
     * @param email El correo electrónico a almacenar.
     */
    fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(EMAIL_KEY, email)
        editor.apply() // Aplica los cambios de forma asíncrona
    }

    /**
     * Recupera el correo electrónico almacenado.
     *
     * @return El correo electrónico del usuario o null si no existe.
     */
    fun getEmail(): String? {
        return sharedPreferences.getString(EMAIL_KEY, null)
    }

    /**
     * Elimina el correo electrónico almacenado.
     */
    fun clearEmail() {
        val editor = sharedPreferences.edit()
        editor.remove(EMAIL_KEY) // Remueve el correo electrónico
        editor.apply() // Aplica los cambios de forma asíncrona
    }
}