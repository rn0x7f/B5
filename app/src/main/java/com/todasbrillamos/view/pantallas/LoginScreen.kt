package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.TextoClickeableOlvideContrasena
import com.todasbrillamos.view.componentes.TextoClickeableRegistro
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton
import com.todasbrillamos.viewmodel.MainVM

/**
 * Pantalla de inicio de sesión.
 * Permite al usuario iniciar sesión en la aplicación.
 * @author Roger Rendón
 * @author Kevin Castro
 */
@Composable
fun LoginScreen(navController: NavController, mainVM: MainVM) {
    // Definir el gradiente
        val gradientColors = listOf(
            Color( 0xFFffe5b4), // Color inicial
            Color(0xFFffbba8)  // Color final
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(start = 28.dp, end = 28.dp, top = 45.dp)

        ) {
            TextoNormal(value = "Hola!")
            TextoResaltado(value = "Bienvenida de vuelta")
            Spacer(modifier = Modifier
                .padding(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagen de bienvenida",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(vertical = 10.dp)
            )

            CampoTexto(
                labelValue = "Correo electronico",
                painterResource(id = R.drawable.mail)
            )
            CampoPassword(
                labelValue = "Contraseña",
                painterResource = painterResource (id = R.drawable.pass)
            )
            TextoClickeableOlvideContrasena()
            Spacer(modifier = Modifier
                .padding(20.dp))
            boton(value = "Iniciar Sesion"){
                navController.navigate("home")

            }
            TextoClickeableRegistro(navController)

        }
    }


@Preview
@Composable
fun PreviewLoginScreen() {
    // Crear un NavController ficticio para la vista previa
    val navController = rememberNavController()
    LoginScreen(navController, MainVM())
}
