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
 * Pantalla de Perfil.
 * El usuario consulta su perfil e información del mismo aquí.
 * @author Roger Rendón
 * @author Kevin Castro
 */

@Composable
fun ProfileScreen(navController: NavHostController) {

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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre elementos
            ) {
                Row{
                    TextoResaltado(value = "Bienvenida")
                }
                // Primer elemento "Mi Cuenta" con Card y elevación

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Elevación de 8dp
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("cuenta")
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile), // Asegúrate de tener este ícono en drawable
                            contentDescription = "Mi Cuenta",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Mi Cuenta",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                // Segundo elemento "Mis Pedidos" con Card y elevación
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Elevación de 8dp
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Acción cuando el usuario clickea en "Mis Pedidos"
                            navController.navigate("pedidos")
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.order),
                            contentDescription = "Mis Pedidos",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Mis Pedidos",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Row {
                    boton(value = "Cerrar sesion") {
                        //ON CLICK CERRAR SESION
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    // Crear un NavController ficticio para la vista previa
    val navController = rememberNavController()
    ProfileScreen(navController)
}
