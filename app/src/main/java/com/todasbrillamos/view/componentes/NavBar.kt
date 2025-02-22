package com.todasbrillamos.view.componentes

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.todasbrillamos.R

/**
 * Componente que muestra una barra de navegación animada.
 *
 * @param navController Controlador de navegación para manejar la navegación entre pantallas.
 */
@Composable
fun NavBar(navController: NavController) {
    // Lista de iconos para la barra de navegación
    val navigationItems = listOf(
        R.drawable.homebar,    // Icono para la pantalla principal
        R.drawable.cartbar,    // Icono para la pantalla del carrito
        R.drawable.about,      // Icono para la pantalla "Acerca"
        R.drawable.userbar     // Icono para la pantalla de usuario
    )

    var selectedIndex by remember { mutableStateOf(0) } // Estado del índice seleccionado

    // Usar LaunchedEffect para observar cambios en la pantalla actual
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            selectedIndex = when (backStackEntry.destination.route) {
                "home" -> 0
                "checkout" -> 1 // Cambiado a "checkout"
                "acerca" -> 2
                "perfil", "pedidos", "cuenta" -> 3
                else -> 0
            }
        }
    }

    AnimatedNavigationBar(
        modifier = Modifier
            .height(68.dp)
            .padding(all = 12.dp),
        selectedIndex = selectedIndex,
        cornerRadius = shapeCornerRadius(cornerRadius = 28.dp),
        ballAnimation = Straight(tween(300)), // Animación de la bola
        indentAnimation = Height(tween(300)), // Animación de la indentación
        barColor = colorResource(id = R.color.rosaTB), // Color de la barra
        ballColor = colorResource(id = R.color.rosaTB) // Color de la bola
    ) {
        // items de navegación
        navigationItems.forEachIndexed { index, item ->
            IconButton(
                onClick = {
                    selectedIndex = index
                    when (index) {
                        0 -> {
                            navController.navigate("home") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        1 -> {
                            navController.navigate("checkout") {  // Cambiado a "checkout"
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        2 -> {
                            navController.navigate("acerca") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        3 -> {
                            navController.navigate("perfil") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = item),
                    contentDescription = null // Descripción de contenido para accesibilidad
                )
            }
        }
    }
}