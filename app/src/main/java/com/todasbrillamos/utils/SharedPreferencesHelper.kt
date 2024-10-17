package com.todasbrillamos.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val TOKEN_KEY = "auth_token"
        private const val EMAIL_KEY = "user_email"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Save token locally
    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    // Retrieve token
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    // Remove token (for logout purposes)
    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }

    fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(EMAIL_KEY, email)
        editor.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(EMAIL_KEY, null)
    }

    fun clearEmail() {
        val editor = sharedPreferences.edit()
        editor.remove(EMAIL_KEY)
        editor.apply()
    }
}