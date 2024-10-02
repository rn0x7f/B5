package com.todasbrillamos.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.utils.SharedPreferencesHelper
import com.todasbrillamos.view.pantallas.*
import com.todasbrillamos.viewmodel.MainVM

/**
 * Contiene las rutas de la app para implementar la navegación
 */
@Composable
fun MainApp(mainVM: MainVM) {
    val navController = rememberNavController()
    val sharedPreferencesHelper = SharedPreferencesHelper(LocalContext.current)

    // NAVHOST
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash")
        {
            SplashScreen(navController)
        }

        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(navController, mainVM, sharedPreferencesHelper)
        }

        // Pantalla de registro
        composable("signup") {
            SignUpScreen(navController, mainVM, sharedPreferencesHelper)
        }

        // Pantalla principal (home)
        composable("home") {
            HomeScreen(navController, mainVM)
        }

        // Pantalla acerca de
        composable("acerca") {
            AcercaScreen(navController)
        }

        // Pantalla de perfil
        composable("perfil") {
            ProfileScreen(navController,sharedPreferencesHelper)
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
