package com.todasbrillamos.view.pantallas


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.viewmodel.MainVM

/**
 * Pantalla de Pedidos de Usuario.
 * El usuario consulta su información de pedidos aquí.
 * @author Roger Rendón
 * @author Kevin Castro
 */

@Composable
fun PedidosScreen(navController: NavHostController, mainVM: MainVM) {

    // Definir gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding)
                .padding(28.dp)
        ) {
            TextoResaltado(value = "Mis pedidos")
            // Lista de opciones en forma de lista vertical
            Column(){


            }
        }
    }
}

@Preview
@Composable
fun PreviewPedidosScreen() {
    // Crear un NavController ficticio para la vista previa
    val navController = rememberNavController()
    PedidosScreen(navController, mainVM = MainVM())
}
