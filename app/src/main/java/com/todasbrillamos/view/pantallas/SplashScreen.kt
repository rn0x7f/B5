package com.todasbrillamos.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.todasbrillamos.utils.SharedPreferencesHelper

/**
 * Pantalla de inicio de la aplicación
 * Esta es una pantalla ambigua que se usa para verificar si el usuario ya ha iniciado sesión
 */
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferencesHelper = SharedPreferencesHelper(context)

    // Check if the token exists
    val token = sharedPreferencesHelper.getToken()

    // Navigate to the appropriate screen
    if (token != null) {
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    } else {
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true } // Clear the splash screen from back stack
        }
    }
}