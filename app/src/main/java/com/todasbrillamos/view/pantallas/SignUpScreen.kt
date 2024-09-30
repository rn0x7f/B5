package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.CheckboxComp
import com.todasbrillamos.view.componentes.TextoClickeableLogin
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton

/**
 * Pantalla de registro de usuarios
 * @author Roger Rendón
 *
 */

@Composable
fun SignUpScreen(navController: NavController) {
    // Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(start = 28.dp, end = 28.dp, top = 45.dp, bottom = 45.dp)
    ) {
        TextoNormal(value = "Hola!")
        TextoResaltado(value = stringResource(id = R.string.CrearCuenta))
        Spacer(modifier = Modifier.height(20.dp))
        CampoTexto(
            labelValue = "Nombre",
            painterResource = painterResource(id = R.drawable.profile)
        )
        CampoTexto(
            labelValue = "Apellido",
            painterResource = painterResource(id = R.drawable.profile)
        )
        CampoTexto(
            labelValue = "Correo electronico",
            painterResource(id = R.drawable.mail)
        )
        CampoTexto(
            labelValue = "Telefono",
            painterResource = painterResource(id = R.drawable.phone)
        )
        CampoPassword(
            labelValue = "Contraseña",
            painterResource = painterResource(id = R.drawable.pass)
        )
        CheckboxComp(value = "Al crear una cuenta, aceptas nuestros términos y condiciones")

        Spacer(modifier = Modifier.height(190.dp))
        boton(value = "Registrar") {
            navController.navigate("home")
        }

        TextoClickeableLogin(navController)
    }
}


@Preview
@Composable
fun PreviewSignUpScreen() {
    // Crear un NavController ficticio para la vista previa
    val navController = rememberNavController()
    SignUpScreen(navController)
}

