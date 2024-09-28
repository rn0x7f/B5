package com.todasbrillamos.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.view.pantallas.*

/**
 * Contiene las rutas de la app para implementar la navegación
 */
@Composable
fun MainApp() {
    val navController = rememberNavController()

    // NAVHOST
    NavHost(navController = navController, startDestination = "login") {

        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(navController = navController)
        }

        // Pantalla de registro
        composable("signup") {
            SignUpScreen(navController = navController)
        }

        // Pantalla principal (home)
        composable("home") {
            HomeScreen(navController)
        }

        // Pantalla acerca de
        composable("acerca") {
            AcercaScreen(navController)
        }

        // Pantalla de perfil
        composable("perfil") {
            ProfileScreen(navController)
        }

        // Pantalla de cuenta
        composable("cuenta") {
            CuentaScreen(navController)
        }

        // Pantalla de pedidos
        composable("pedidos") {
            PedidosScreen(navController)
        }
    }
}
