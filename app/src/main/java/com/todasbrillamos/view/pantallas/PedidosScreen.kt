package com.todasbrillamos.view.pantallas


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton

/**
 * Pantalla de Pedidos de Usuario.
 * El usuario consulta su información de pedidos aquí.
 * @author Roger Rendón
 * @author Kevin Castro
 */

@Composable
fun PedidosScreen(navController: NavHostController) {

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
    PedidosScreen(navController)
}
