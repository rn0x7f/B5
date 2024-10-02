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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton

/**
 * Pantalla de Cuenta de Usuario.
 * El usuario consulta su información de cuenta aquí.
 * @author Roger Rendón
 * @author Kevin Castro
 */

@Composable
fun CuentaScreen(navController: NavHostController) {
    // Definir gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    // Estado para los campos de texto
    val nombre = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }

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
            // Campos de texto para el nombre, email y teléfono
            CampoTexto(
                labelValue = "Nombre",
                painterResource = painterResource(id = R.drawable.profile),
                textValue = nombre.value,
                onValueChange = { nombre.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Apellido",
                painterResource = painterResource(id = R.drawable.profile),
                textValue = nombre.value,
                onValueChange = { nombre.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Correo electrónico",
                painterResource = painterResource(id = R.drawable.mail),
                textValue = email.value,
                onValueChange = { email.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Teléfono",
                painterResource = painterResource(id = R.drawable.phone),
                textValue = telefono.value,
                onValueChange = { telefono.value = it }
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Botón para actualizar los datos
            boton(value = "Actualizar datos") {
                // Acción para actualizar los datos del usuario
                // Aquí puedes añadir la lógica para actualizar los datos
            }
        }
    }
}

@Preview
@Composable
fun PreviewCuentaScreen() {
    // Crear un NavController ficticio para la vista previa
    val navController = rememberNavController()
    CuentaScreen(navController)
}
