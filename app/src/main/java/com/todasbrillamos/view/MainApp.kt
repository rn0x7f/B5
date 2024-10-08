package com.todasbrillamos.view

import CardScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stripe.android.Stripe
import com.todasbrillamos.utils.SharedPreferencesHelper
import com.todasbrillamos.view.pantallas.*
import com.todasbrillamos.viewmodel.MainVM

/**
 * Contiene las rutas de la app para implementar la navegación
 */
@Composable
fun MainApp(mainVM: MainVM, stripe: Stripe) {
    // Crear un NavController para manejar la navegación
    val navController = rememberNavController()
    // Crear un helper para las preferencias compartidas (SharedPreferences)
    val sharedPreferencesHelper = SharedPreferencesHelper(LocalContext.current)

    // NavHost define las rutas de la aplicación
    NavHost(navController = navController, startDestination = "splash") {

        // Pantalla Splash
        composable("splash") {
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
            ProfileScreen(navController, sharedPreferencesHelper)
        }

        // Pantalla de cuenta
        composable("cuenta") {
            CuentaScreen(navController,mainVM)
        }

        composable("checkout") {
            CarritoScreen(navController, mainVM)
        }

        composable("pago/{total}/{description}") { backStackEntry ->
            val total: Float = backStackEntry.arguments?.getString("total")?.toFloatOrNull() ?: 0f
            val description: String = backStackEntry.arguments?.getString("description") ?: ""
            CardScreen(stripe, navController, mainVM, total,description)
        }

        // Pantalla de pedidos
        composable("pedidos") {
            PedidosScreen(navController, mainVM)
        }

        // Pantalla de detalle de item, donde el ID del producto es pasado como argumento
        composable(
            "item/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Obtenemos el productId desde los argumentos
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                // Navegar a la pantalla de detalles del producto con el ID pasado
                ItemScreen(navController = navController, productID = productId, mainVM)
            } else {
                // En caso de que el productId sea null (precaución adicional)
            }
        }
    }
}
