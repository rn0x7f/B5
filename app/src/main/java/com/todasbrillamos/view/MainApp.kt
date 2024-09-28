package com.todasbrillamos.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.view.pantallas.AcercaScreen
import com.todasbrillamos.view.pantallas.CuentaScreen
import com.todasbrillamos.view.pantallas.HomeScreen
import com.todasbrillamos.view.pantallas.PedidosScreen
import com.todasbrillamos.view.pantallas.ProfileScreen

//contiene todas las pantallas de la app

/**
 * Contiene la pantalla principal de la app.
 * @param modifier modificador para personalizar la apariencia de la pantalla.
 */
@Composable
fun MainApp() {
    val navController = rememberNavController()

    // NAVHOST
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("acerca") { AcercaScreen(navController) }
        composable("perfil") { ProfileScreen(navController) }
        composable("cuenta") { CuentaScreen(navController) }
        composable("pedidos") { PedidosScreen(navController) }

    }

}

